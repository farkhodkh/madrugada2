package ru.petrolplus.pos.persitence

/**
 * Обобщающий интерфейс для доступа к различным настройкам устройства
 */
interface SettingsPersistence : BaseSettingsPersistence, CommonSettingsPersistence,
    GUIDParamsPersistence, ShiftParamsPersistence, ReceiptParamsPersistence

/**
 * Реализация "пустышка", по сути является фасадом для отдельных настроек,
 * используется механизм делегирования Котлин, что позволяет работать с SettingsPersistence как
 * монолитной конструкцией когда вся реализация разделена на конкретные настройки.
 */
class SettingsPersistenceImpl(
    private val baseSettingsPersistence: BaseSettingsPersistence,
    private val commonSettingsPersistence: CommonSettingsPersistence,
    private val guiDparamsPersistence: GUIDParamsPersistence,
    private val shiftParamsPersistence: ShiftParamsPersistence,
    private val receiptParamsPersistence: ReceiptParamsPersistence
) : SettingsPersistence,
    BaseSettingsPersistence by baseSettingsPersistence,
    CommonSettingsPersistence by commonSettingsPersistence,
    GUIDParamsPersistence by guiDparamsPersistence,
    ShiftParamsPersistence by shiftParamsPersistence,
    ReceiptParamsPersistence by receiptParamsPersistence