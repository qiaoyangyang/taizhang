package com.meiling.account.eventBusData

import com.meiling.common.network.data.SelectDialogDto
import com.meiling.common.network.data.SelectOrderDialogDto

class MessageEventTimeShow()
class MessageEventPayMoney(val type: String)
class MessageEventTime(val starTime: String, val endTime: String)
class MessageEventVoucherInspectionHistory(val id: Int)
class MessageEventUpDataTip()
class MessageEventHistoryUpDataTip()
class MessageEventTabChange()

class MessageHistoryEventSelect(var selectDialogDto: SelectDialogDto,var shopId: String)
class MessageOrderEventSelect(var selectDialogDto: SelectOrderDialogDto, var shopId: String)

class MessageSelectShopPo(var idArrayList: ArrayList<String>)

