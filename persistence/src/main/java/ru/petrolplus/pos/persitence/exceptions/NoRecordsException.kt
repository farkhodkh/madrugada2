package ru.petrolplus.pos.persitence.exceptions

import ru.petrolplus.pos.persitence.R
import ru.petroplus.pos.util.ResourceHelper

object NoRecordsException : IllegalStateException(ResourceHelper.getStringResource(R.string.no_records_exception))