package ir.arminniromandi.mybuilding.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class ThousandSeparatorTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text


        val formattedText = originalText.reversed()
            .chunked(3)
            .joinToString(",")
            .reversed()

        return TransformedText(
            AnnotatedString(formattedText),
            // مکان‌نما رو انتهای متن نگه می‌داریم
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int = formattedText.length
                override fun transformedToOriginal(offset: Int): Int = originalText.length
            }
        )
    }
}
