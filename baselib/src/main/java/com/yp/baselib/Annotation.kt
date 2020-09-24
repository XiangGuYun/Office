package com.yp.baselib

/**
 * 在对应Activity或Fragment的方法中注册和解除注册EventBus
 */
@Target(AnnotationTarget.CLASS)
annotation class Bus

/**
 * 设置状态栏文字为黑色
 */
@Target(AnnotationTarget.CLASS)
annotation class StatusBarBlackText

/**
 * 全屏显示
 */
@Target(AnnotationTarget.CLASS)
annotation class FullScreen

/**
 * 不设置FitSystemWindow为true
 */
@Target(AnnotationTarget.CLASS)
annotation class NotFitSystemWindow

/**
 * 不设置屏幕方向
 */
@Target(AnnotationTarget.CLASS)
annotation class NoReqOrientation

/**
 * 状态栏颜色
 * @property color String
 * @constructor
 */
@Target(AnnotationTarget.CLASS)
annotation class StatusBarColor(val color: String)

/**
 * 布局Id
 * @property id Int
 * @constructor
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class LayoutId(val id: Int)

/**
 * 设置屏幕方向，默认是竖向
 * @property isVertical Boolean
 * @constructor
 */
@Target(AnnotationTarget.CLASS)
annotation class Orientation(val isVertical: Boolean)

/**
 * 设置沉浸式状态栏
 * @property topBarId Int
 * @constructor
 */
annotation class Immersion(val topBarId:Int)

annotation class ReqPermissions(val permissions: Array<String>)
