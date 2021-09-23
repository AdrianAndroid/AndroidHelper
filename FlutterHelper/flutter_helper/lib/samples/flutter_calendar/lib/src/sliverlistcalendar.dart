import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';

import 'calendar.dart';

/// A sliver that places multiple box children in a linear array along the main
/// axis.
///
/// Each child is forced to have the [SliverConstraints.crossAxisExtent] in the
/// cross axis but determines its own main axis extent.
///
/// [RenderSliverList] determines its scroll offset by "dead reckoning" because
/// children outside the visible part of the sliver are not materialized, which
/// means [RenderSliverList] cannot learn their main axis extent. Instead, newly
/// materialized children are placed adjacent to existing children. If this dead
/// reckoning results in a logical inconsistency (e.g., attempting to place the
/// zeroth child at a scroll offset other than zero), the [RenderSliverList]
/// generates a [SliverGeometry.scrollOffsetCorrection] to restore consistency.
///
/// If the children have a fixed extent in the main axis, consider using
/// [RenderSliverFixedExtentList] rather than [RenderSliverList] because
/// [RenderSliverFixedExtentList] does not need to perform layout on its
/// children to obtain their extent in the main axis and is therefore more
/// efficient.
///
/// See also:
///
///  * [RenderSliverFixedExtentList], which is more efficient for children with
///    the same extent in the main axis.
///  * [RenderSliverGrid], which places its children in arbitrary positions.
class RenderSliverCenterList extends RenderSliverMultiBoxAdaptor {
  /// Creates a sliver that places multiple box children in a linear array along
  /// the main axis.
  ///
  /// The [childManager] argument must not be null.
  RenderSliverCenterList({
    @required RenderSliverBoxChildManager childManager,
    @required this.state,
    this.startIndex = 0,
  }) : super(childManager: childManager);

  final int startIndex;
  final CalendarWidgetState state;
  int newTopScrollIndex;

  @override
  void performLayout() {
    childManager.didStartLayout();
    childManager.setDidUnderflow(false);

    final double scrollOffset = constraints.scrollOffset;
    assert(scrollOffset >= 0.0);
    final double remainingPaintExtent = constraints.remainingPaintExtent;
    assert(remainingPaintExtent >= 0.0);
    double targetEndScrollOffset = scrollOffset + remainingPaintExtent;
    final BoxConstraints childConstraints = constraints.asBoxConstraints();
    int leadingGarbage = 0;
    int trailingGarbage = 0;
    bool reachedEnd = false;
    bool forceScrollUpdate = false;

    debugPrint('new top $newTopScrollIndex');

    // This algorithm in principle is straight-forward: find the first child
    // that overlaps the given scrollOffset, creating more children at the top
    // of the list if necessary, then walk down the list updating and laying out
    // each child and adding more at the end if necessary until we have enough
    // children to cover the entire viewport.
    //
    // It is complicated by one minor issue, which is that any time you update
    // or create a child, it's possible that the some of the children that
    // haven't yet been laid out will be removed, leaving the list in an
    // inconsistent state, and requiring that missing nodes be recreated.
    //
    // To keep this mess tractable, this algorithm starts from what is currently
    // the first child, if any, and then walks up and/or down from there, so
    // that the nodes that might get removed are always at the edges of what has
    // already been laid out.

    //print('offset $scrollOffset');

    // Make sure we have at least one child to start from.
    if (firstChild == null) {
      //print('Making first child');
      if (!addInitialChild(index: startIndex, layoutOffset: scrollOffset)) {
        // There are no children.
        geometry = SliverGeometry.zero;
        childManager.didFinishLayout();
        return;
      }
    }

    // We have at least one child.

    // These variables track the range of children that we have laid out. Within
    // this range, the children have consecutive indices. Outside this range,
    // it's possible for a child to get removed without notice.
    RenderBox leadingChildWithLayout, trailingChildWithLayout;

    // Find the last child that is at or before the scrollOffset.
    RenderBox earliestUsefulChild = firstChild;
    if (newTopScrollIndex != null) {
      final SliverMultiBoxAdaptorParentData childParentData =
          firstChild.parentData as SliverMultiBoxAdaptorParentData;
      if (childParentData.index == newTopScrollIndex) {
        newTopScrollIndex = null;
        if (childParentData.layoutOffset != scrollOffset) {
          geometry = SliverGeometry(
            scrollOffsetCorrection:
                childParentData.layoutOffset - scrollOffset,
          );
        }
        debugPrint('$geometry $scrollOffset ${childParentData.layoutOffset}');
        //sharedState.controller.jumpTo(childParentData.layoutOffset);
        return;
      }
    }

    for (double earliestScrollOffset = childScrollOffset(earliestUsefulChild);
        earliestScrollOffset > scrollOffset;
        earliestScrollOffset = childScrollOffset(earliestUsefulChild)) {
      // We have to add children before the earliestUsefulChild.
      earliestUsefulChild =
          insertAndLayoutLeadingChild(childConstraints, parentUsesSize: true);

      if (earliestUsefulChild == null) {
        final SliverMultiBoxAdaptorParentData childParentData =
            firstChild.parentData as SliverMultiBoxAdaptorParentData;
        childParentData.layoutOffset = 0.0;
        //print("earlierusefulchild ${childParentData.layoutOffset}");

        if (scrollOffset == 0.0) {
          earliestUsefulChild = firstChild;
          leadingChildWithLayout = earliestUsefulChild;
          trailingChildWithLayout ??= earliestUsefulChild;
          break;
        } else {
          // We ran out of children before reaching the scroll offset.
          // We must inform our parent that this sliver cannot fulfill
          // its contract and that we need a scroll offset correction.
          geometry = SliverGeometry(
            scrollOffsetCorrection: -scrollOffset,
          );
          //print("Geometry correct ${childParentData.layoutOffset}");
          return;
        }
      }

      final double firstChildScrollOffset =
          earliestScrollOffset - paintExtentOf(firstChild);
      if (firstChildScrollOffset < 0.0) {
        // The first child doesn't fit within the viewport (underflow) and
        // there may be additional children above it. Find the real first child
        // and then correct the scroll position so that there's room for all and
        // so that the trailing edge of the original firstChild appears where it
        // was before the scroll offset correction.
        // TODO(hansmuller): do this work incrementally, instead of all at once,
        // i.e. find a way to avoid visiting ALL of the children whose offset
        // is < 0 before returning for the scroll correction.
        double correction = 0.0;
        while (earliestUsefulChild != null) {
          assert(firstChild == earliestUsefulChild);
          correction += paintExtentOf(firstChild);
          earliestUsefulChild = insertAndLayoutLeadingChild(childConstraints,
              parentUsesSize: true);
        }
        geometry = SliverGeometry(
          scrollOffsetCorrection: correction - earliestScrollOffset,
        );
        final SliverMultiBoxAdaptorParentData childParentData =
            firstChild.parentData as SliverMultiBoxAdaptorParentData;
        childParentData.layoutOffset = 0.0;
        //print("correcting here ${childParentData.layoutOffset} == 0.0");

        return;
      }

      final SliverMultiBoxAdaptorParentData childParentData =
          earliestUsefulChild.parentData as SliverMultiBoxAdaptorParentData;
      childParentData.layoutOffset = firstChildScrollOffset;
      //print("layout offset update ${childParentData.layoutOffset} == 0.0");

      assert(earliestUsefulChild == firstChild);
      leadingChildWithLayout = earliestUsefulChild;
      trailingChildWithLayout ??= earliestUsefulChild;
      // If we hit the point for the scroll, then we use that.
      //print('scroll up $newTopScrollIndex $childParentData');
      if (newTopScrollIndex != null) {
        if (childParentData.index == newTopScrollIndex) {
          newTopScrollIndex = null;
          // Fix the geometry to match where we are.
          if (childParentData.layoutOffset != scrollOffset) {
            geometry = SliverGeometry(
              scrollOffsetCorrection:
                  childParentData.layoutOffset - scrollOffset,
            );
          }
          debugPrint('$geometry $scrollOffset ${childParentData.layoutOffset}');
          return;
        }
      }
    }

    // At this point, earliestUsefulChild is the first child, and is a child
    // whose scrollOffset is at or before the scrollOffset, and
    // leadingChildWithLayout and trailingChildWithLayout are either null or
    // cover a range of render boxes that we have laid out with the first being
    // the same as earliestUsefulChild and the last being either at or after the
    // scroll offset.

    assert(earliestUsefulChild == firstChild);
    assert(childScrollOffset(earliestUsefulChild) <= scrollOffset);

    if (earliestUsefulChild != null) {
      final SliverMultiBoxAdaptorParentData parentData =
          earliestUsefulChild.parentData as SliverMultiBoxAdaptorParentData;
      state.currentTopDisplayIndex = parentData.index ~/ 2;
    }

    // Make sure we've laid out at least one child.
    if (leadingChildWithLayout == null) {
      earliestUsefulChild.layout(childConstraints, parentUsesSize: true);
      leadingChildWithLayout = earliestUsefulChild;
      trailingChildWithLayout = earliestUsefulChild;
    }

    // Here, earliestUsefulChild is still the first child, it's got a
    // scrollOffset that is at or before our actual scrollOffset, and it has
    // been laid out, and is in fact our leadingChildWithLayout. It's possible
    // that some children beyond that one have also been laid out.

    bool inLayoutRange = true;
    RenderBox child = earliestUsefulChild;
    int index = indexOf(child);
    double endScrollOffset = childScrollOffset(child) + paintExtentOf(child);
    bool advance() {
      // returns true if we advanced, false if we have no more children
      // This function is used in two different places below, to avoid code duplication.
      assert(child != null);
      if (child == trailingChildWithLayout) {
        inLayoutRange = false;
      }
      child = childAfter(child);
      if (child == null) {
        inLayoutRange = false;
      }
      index += 1;
      if (!inLayoutRange) {
        if (child == null || indexOf(child) != index) {
          // We are missing a child. Insert it (and lay it out) if possible.
          child = insertAndLayoutChild(
            childConstraints,
            after: trailingChildWithLayout,
            parentUsesSize: true,
          );
          if (child == null) {
            // We have run out of children.
            debugPrint('No children $geometry');
            return false;
          }
        } else {
          // Lay out the child.
          child.layout(childConstraints, parentUsesSize: true);
        }
        trailingChildWithLayout = child;
      }
      assert(child != null);
      final SliverMultiBoxAdaptorParentData childParentData =
          child.parentData as SliverMultiBoxAdaptorParentData;
      childParentData.layoutOffset = endScrollOffset;
      assert(childParentData.index == index);
      endScrollOffset = childScrollOffset(child) + paintExtentOf(child);
      //print('froggy frogg ${childParentData.layoutOffset}');
      return true;
    }

    // Find the first child that ends after the scroll offset.
    while (endScrollOffset < scrollOffset) {
      leadingGarbage += 1;
      if (!advance()) {
        assert(leadingGarbage == childCount);
        assert(child == null);
        // we want to make sure we keep the last child around so we know the end scroll offset
        collectGarbage(leadingGarbage - 1, 0);
        assert(firstChild == lastChild);
        final double extent =
            childScrollOffset(lastChild) + paintExtentOf(lastChild);
        geometry = SliverGeometry(
          scrollExtent: extent,
          paintExtent: 0.0,
          maxPaintExtent: extent,
        );
        //print("Stuff here");
        return;
      }

      // Check here to see if we should drop out because we hit the endpoint.
      if (newTopScrollIndex != null) {
        SliverMultiBoxAdaptorParentData childParentData =
            lastChild.parentData as SliverMultiBoxAdaptorParentData;
        debugPrint(
            'top scroll offset! 2 $newTopScrollIndex ${childParentData.index}');
        if (childParentData.index == newTopScrollIndex) {
          newTopScrollIndex = null;

          // Fix the geometry to match where we are.
          if (childParentData.layoutOffset != scrollOffset) {
            geometry = SliverGeometry(
              scrollOffsetCorrection:
                  childParentData.layoutOffset - scrollOffset,
            );
          }
          // Force it to layout down to the bottom of this page.
          targetEndScrollOffset = endScrollOffset + remainingPaintExtent;
          debugPrint('$geometry $scrollOffset ${childParentData.layoutOffset}');
          forceScrollUpdate = true;
          break;
        }
      }
    }

    // Now find the first child that ends after our end.
    while (endScrollOffset < targetEndScrollOffset) {
      if (!advance()) {
        reachedEnd = true;
        break;
      }
    }

    // Finally count up all the remaining children and label them as garbage.
    if (child != null) {
      child = childAfter(child);
      while (child != null) {
        trailingGarbage += 1;
        child = childAfter(child);
      }
    }

    // At this point everything should be good to go, we just have to clean up
    // the garbage and report the geometry.

    collectGarbage(leadingGarbage, trailingGarbage);

    assert(debugAssertChildListIsNonEmptyAndContiguous());
    double estimatedMaxScrollOffset;
    if (reachedEnd) {
      estimatedMaxScrollOffset = endScrollOffset;
    } else {
      estimatedMaxScrollOffset = childManager.estimateMaxScrollOffset(
        constraints,
        firstIndex: indexOf(firstChild),
        lastIndex: indexOf(lastChild),
        leadingScrollOffset: childScrollOffset(firstChild),
        trailingScrollOffset: endScrollOffset,
      );
      assert(estimatedMaxScrollOffset >=
          endScrollOffset - childScrollOffset(firstChild));
    }
    final double paintExtent = calculatePaintOffset(
      constraints,
      from: childScrollOffset(firstChild),
      to: endScrollOffset,
    );

    SliverMultiBoxAdaptorParentData childParentData =
        firstChild.parentData as SliverMultiBoxAdaptorParentData;
    geometry = SliverGeometry(
      scrollOffsetCorrection: forceScrollUpdate
          ? childParentData.layoutOffset - scrollOffset
          : null,
      scrollExtent: estimatedMaxScrollOffset,
      paintExtent: paintExtent,
      maxPaintExtent: estimatedMaxScrollOffset,
      // Conservative to avoid flickering away the clip during scroll.
      hasVisualOverflow: endScrollOffset > targetEndScrollOffset ||
          constraints.scrollOffset > 0.0,
    );
    //print('fluffy rabbits $geometry');

    // We may have started the layout while scrolled to the end, which would not
    // expose a new child.
    if (estimatedMaxScrollOffset == endScrollOffset) {
      childManager.setDidUnderflow(true);
    }
    childManager.didFinishLayout();
  }

  @override
  double childCrossAxisPosition(RenderBox child) {
    //print('render $child ${super.childCrossAxisPosition(child)}');
    return super.childCrossAxisPosition(child);
  }
}

class SliverListCenter extends SliverMultiBoxAdaptorWidget {
  /// Creates a sliver that places box children in a linear array.
  const SliverListCenter({
    @required SliverChildDelegate delegate,
    @required this.controller,
    @required this.state,
    Key key,
    this.startIndex = 0,
  }) : super(key: key, delegate: delegate);

  @override
  RenderSliverCenterList createRenderObject(BuildContext context) {
    final SliverMultiBoxAdaptorElement element =
        context as SliverMultiBoxAdaptorElement;
    RenderSliverCenterList ret = RenderSliverCenterList(
        childManager: element, startIndex: startIndex, state: state);
    state.renderSliverList = ret;
    return ret;
  }

  final int startIndex;
  final ScrollController controller;
  final CalendarWidgetState state;

  /// Returns an estimate of the max scroll extent for all the children.
  ///
  /// Subclasses should override this function if they have additional
  /// information about their max scroll extent.
  ///
  /// This is used by [SliverMultiBoxAdaptorElement] to implement part of the
  /// [RenderSliverBoxChildManager] API.
  ///
  /// The default implementation defers to [delegate] via its
  /// [SliverChildDelegate.estimateMaxScrollOffset] method.
  @override
  double estimateMaxScrollOffset(
    SliverConstraints constraints,
    int firstIndex,
    int lastIndex,
    double leadingScrollOffset,
    double trailingScrollOffset,
  ) {
    assert(lastIndex >= firstIndex);
    return delegate.estimateMaxScrollOffset(
      firstIndex,
      lastIndex,
      leadingScrollOffset,
      trailingScrollOffset,
    );
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
        .add(DiagnosticsProperty<SliverChildDelegate>('delegate', delegate));
  }
}
