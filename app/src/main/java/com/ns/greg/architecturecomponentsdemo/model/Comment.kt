package com.ns.greg.architecturecomponentsdemo.model

import java.util.Date

/**
 * @author gregho
 * @since 2018/6/4
 */
interface Comment {

  fun getId(): Int?

  fun getProductId(): Int?

  fun getText(): String?

  fun getPostedAt(): Date?
}