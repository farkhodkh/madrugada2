package ru.petrolplus.pos.persitence.exceptions

import ru.petrolplus.pos.resources.R
import ru.petrolplus.pos.resources.ResourceHelper

object NoRecordsException : IllegalStateException(ResourceHelper.getStringResource(R.string.no_records_exception))
