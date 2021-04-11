1.
onTouch
onClickListener
         performClickInternal();

2. 分发 
onInterruptTouchEvent
ListView+ViewPager

3. 

	    ListenerInfo li = mListenerInfo;
            if (li != null && li.mOnTouchListener != null
                    && (mViewFlags & ENABLED_MASK) == ENABLED
                    && li.mOnTouchListener.onTouch(this, event)) {
                result = true;
            }

            if (!result && onTouchEvent(event)) {
                result = true;
            }


Down--只有一个
总经理
1. 判断事件是否拦截
2. 不拦截：分发
	1. down事件才会分发
	2.	target.next = null --> next = null
		newTouchTarget == mFirstTouchTarget != null
		alreadyDispatchedToNewTouchTarget = true
3. 分发或处理
	1. 拦截：相当于你是最后一个，事件到底初步处理（全部不处理和拦截流程一样）
	2.	if(mFirstTouchTarget == null) {
			handled = dispatchTransformedToucheEvent(ev, cancelded, null, TouchTarget.ALL_POINTER_IDS);
		}
	3. 不拦截：else --> while 循环只循环一次


MOVE  -- 不会分发事件
	3. 分发预处理--》else
		dispatchTransformedTouchEvent
	alreadyDispatchedToNewTouchTarget = false
	//target.child  保存的子view
	

	拦截--处理事件冲突  只能再move的时候处理
	内部拦截：子View处理  disallowIntercept  -- 设置的标志位
		rqeustDisallowInterceptTouchEvent
		listview.cancel事件（事件被上层触发）
	外部拦截：父容器处理

	第一个Move事件的目的  取消listview.cancel事件 -- mFirstTouchTarget == null 这个时候View没处理
	第二个move事件 

UP
	interrupt了之后， UP事件就在DecorView挡下了	


ACTION_DOWN  ：手指初次接触到屏幕时候触发
ACTION_MOVE   ：手指再屏幕上滑动时触发，会多次触发
ACTION_UP          ：手指离开屏幕时触发
ACTION_CANCEL：事件被上层拦截时触发

问题1：拦截之后，mFirstTouchEvent 怎么是null
	答：拦截之后，mFristTouchEvent没有赋值， 也就不会向下传递



