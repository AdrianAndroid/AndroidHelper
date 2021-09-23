import 'dart:async';

import 'package:flutter/material.dart';
import 'package:sensors/sensors.dart';

import 'dough.dart';
import 'dough_controller.dart';
import 'dough_recipe.dart';

/// A widget that stretches its child in a dough-like fashion based
/// on physical device accelerometer inputs (e.g. the [Dough] jiggles
/// when you move your phone around).
///
/// **This widget ONLY works on devices that have accelerometers.**
/// 
/// Please note that this widget will be moved over to a separate package
/// to remove a platform depedency.
class GyroDough extends StatefulWidget {
  /// Creates a [GyroDough] widget.
  const GyroDough({
    @required key,
    @required this.child,
  }) : super(key: key);

  /// The child to stretch based on physical device motion.
  final Widget child;

  @override
  _GyroDoughState createState() => _GyroDoughState();
}

/// The state of a gyro dough widget which is used to track and interpret
/// accelerometer values.
class _GyroDoughState extends State<GyroDough> {
  final _controller = DoughController();

   List<Offset> _rollingSamples;
   Offset _rollingSum;
   int _rollingIndex;
  bool _hasInitialized = false;
  StreamSubscription<dynamic> _accelSub;

  @override
  void initState() {
    _accelSub = accelerometerEvents.listen(_onAccelEvent);
    _controller.start(
      origin: Offset.zero,
      target: Offset.zero,
    );

    super.initState();
  }

  @override
  void dispose() {
    _accelSub?.cancel();
    _accelSub = null;
    super.dispose();
  }

  @override
  void didChangeDependencies() {
    final prefs = DoughRecipe.watch(context).gyroPrefs;

    if (!_hasInitialized) {
      _rollingSum = Offset.zero;
      _rollingIndex = 0;
      _rollingSamples = List<Offset>.filled(
        prefs.sampleCount,
        Offset.zero,
      );

      _hasInitialized = true;
    } else {
      _rollingSum = Offset.zero;

      final oldSamples = _rollingSamples;
      final newSamples = List<Offset>.filled(
        prefs.sampleCount,
        Offset.zero,
      );

      // Sync the samples to the new gyro preferences.
      for (var i = 0; i < newSamples.length; ++i) {
        newSamples[i] = oldSamples[i % oldSamples.length];
        _rollingSum += newSamples[i];
      }

      _rollingSamples = newSamples;
      _rollingIndex = _rollingIndex % newSamples.length;
    }

    super.didChangeDependencies();
  }

  @override
  Widget build(BuildContext context) {
    return Dough(
      controller: _controller,
      child: widget.child,
    );
  }

  void _onAccelEvent(AccelerometerEvent event) {
    final prefs = DoughRecipe.read(context).gyroPrefs;
    final sample = Offset(-event.x, event.y) * prefs.gyroMultiplier;

    _rollingIndex = (_rollingIndex + 1) % _rollingSamples.length;
    _rollingSum -= _rollingSamples[_rollingIndex];
    _rollingSamples[_rollingIndex] = sample;
    _rollingSum += sample;

    // Apply a low-pass filter to smooth out the accelerometer values.
    _controller.update(
      target: _rollingSum / _rollingSamples.length.toDouble(),
    );
  }
}
