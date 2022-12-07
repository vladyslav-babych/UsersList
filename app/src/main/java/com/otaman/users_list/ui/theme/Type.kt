package com.otaman.users_list.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.otaman.users_list.R

private val fontFamilyInter = FontFamily(
    listOf(
        Font(
            resId = R.font.inter_regular,
            weight = FontWeight.Normal
        ),
        Font(
            resId = R.font.inter_semibold,
            weight = FontWeight.SemiBold
        )
    )
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = fontFamilyInter,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    h1 = TextStyle(
        fontFamily = fontFamilyInter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    )
)