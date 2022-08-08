package com.example.avasttest

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.avasttest.databinding.ActivityBitManipulationBinding

class BitManipulationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBitManipulationBinding

    @SuppressLint("StringFormatMatches")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBitManipulationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.numberOneEditText.doOnTextChanged { text, _, _, _ ->
            text?.let { charSequence ->
                binding.countButton.isEnabled = charSequence.isNotEmpty() && binding.numberTwoEditText.text.isNotEmpty()
            }
        }

        binding.numberTwoEditText.doOnTextChanged { text, _, _, _ ->
            text?.let { charSequence ->
                binding.countButton.isEnabled = charSequence.isNotEmpty() && binding.numberOneEditText.text.isNotEmpty()
            }
        }

        binding.countButton.setOnClickListener {
            val numberOne = binding.numberOneEditText.text.toString().toInt()
            val numberTwo = binding.numberTwoEditText.text.toString().toInt()
            val numberOfBits = calculateTheNumberOfBits(numberOne xor numberTwo)
            binding.outputTextView.text = getString(R.string.text_output, numberOfBits)
        }
    }

    private fun calculateTheNumberOfBits(n: Int): Int {
        var count = 0
        var temp = n
        while (temp != 0) {
            count += temp and 1
            temp = temp shr 1
        }
        return count
    }
}