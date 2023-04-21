package com.meiling.oms.eventBusData

import com.meiling.common.network.data.SelectDialogDto

class MessageEventTimeShow()
class MessageEventPayMoney(val type: String)
class MessageEventTime(val starTime: String, val endTime: String)
class MessageEventVoucherInspectionHistory(val id: Int)
class MessageEventUpDataTip()
class MessageEventHistoryUpDataTip()
class MessageEventTabChange()

class MessageHistoryEventSelect(var selectDialogDto: SelectDialogDto)

class MessageSelectShopPo(var idArrayList: ArrayList<String>)

