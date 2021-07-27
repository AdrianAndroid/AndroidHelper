/*
 * 可以用来获取任意的样式
 */
function getStyle(obj, name) {

	return window.getComputedStyle && getComputedStyle(obj, null)[name] || obj.currentStyle[name];
}

/*
 * 提取出一个函数，可以处理一些简单的动画效果
 * 参数：
 * 		obj: 要执行动画的元素
 * 		attr: 执行动画是要修改的属性
 * 		target: 执行动画的目标位置
 * 		speed: 执行动画的速度
 * 		callback: 回调函数，这个函数将会在动画执行完毕之后被调用
 */
function move(obj, attr, target, speed, callback) {
	//开启定时器前，关闭上一个
	/*
	 * 这里我们的timer是一个全局变量，所有的动画执行时，都会共享同一个timer
	 * 	这样将会导致我们执行box2的动画时，会使box1的动画也立即终止
	 * 	所以我们定时器的标识不能作为全局变量保存，而应该保存到要执行动画的对象上
	 */
	clearInterval(obj.timer);

	//获取当前值，动画执行的起始位置
	var current = parseInt(getStyle(obj, attr));
	//起始位置   大于  目标位置 speed为负
	//起始位置  小于 目标位置 speed为正
	if(current > target) {
		//此时speed应该是负数
		speed = -speed;
	}

	//开启一个定时器，用来移动box1
	obj.timer = setInterval(function() {
		//获取box1的left属性值
		var oldValue = parseInt(getStyle(obj, attr));
		//修改值
		var newValue = oldValue + speed;
		//如果newValue大于800
		//如果向右移动，则newValue > target  speed为正
		//如果向左移动，则newValue < target  speed为负
		if(speed > 0 && newValue > target || speed < 0 && newValue < target) {
			newValue = target
		}
		//将其赋值给box1
		obj.style[attr] = newValue + "px";
		//当运行800px时，停止执行动画
		if(newValue == target) {
			clearInterval(obj.timer);
			//动画执行完毕，调用回调函数
			callback && callback();

		}

	}, 30);
}

/*
 * 定义一个函数，专门用来为一个元素添加class属性值
 * 参数
 * 	obj 要添加class属性的元素
 * 	cn 要添加的class的值
 * 	
 */
function addClass(obj, cn) {

	//判断obj中是否含有cn这个class
	if(!hasClass(obj, cn)) {
		obj.className += " " + cn;
	}

}

/*
 * 判断一个对象中是否含有指定的class属性
 * 	参数：
 * 		obj：要检查的对象
 * 		cn：要检查class值
 * 如果对象中具有该class则返回true，否则返回false
 */
function hasClass(obj, cn) {

	//检查obj中是否与b2这个class
	//var reg = /\bb2\b/;
	var reg = new RegExp("\\b" + cn + "\\b");

	return reg.test(obj.className);

}

/*
 * 删除一个元素中的class
 */
function removeClass(obj, cn) {

	//创建一个正则表达式
	var reg = new RegExp("\\b" + cn + "\\b");

	//将class属性中符合正则表达式的内容，替换为空串
	obj.className = obj.className.replace(reg, "");

}

/*
 * 切换一个元素的class属性值
 * 	如果有，则删除
 * 	如果没有，则添加
 */
function toggleClass(obj, cn) {
	//判断obj中是否有cn
	if(hasClass(obj, cn)) {
		//如果有，则删除
		removeClass(obj, cn);
	} else {
		//如果没有，则添加
		addClass(obj, cn);
	}
}