package com.example.avasttest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.widget.doOnTextChanged
import com.example.avasttest.databinding.ActivityMainBinding
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val dictionary = mutableListOf<String>().apply {
        add("apple")
        add("pie")
        add("shoe")
        add("second")
        add("secondary")
    }

    private val result = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.inputEditText.doOnTextChanged { text, _, _, _ ->
            text?.let { charSequence ->
                binding.parseButton.isEnabled = charSequence.isNotEmpty()
            }
        }

        binding.parseButton.setOnClickListener {
            wordBreak(binding.inputEditText.text.toString(), dictionary)
            binding.resultTextview.text = getString(R.string.text_output, result.toString())
        }

        binding.bitManipulationButton.setOnClickListener {
            Intent(this, BitManipulationActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    // dynamic programing solution with with time complexity O(n^2) && space complexity O(n)
    private fun wordBreak(input: String, dictionary: List<String>): Boolean {
        if (result.isNotEmpty()) result.clear()
        val dp = BooleanArray(input.length + 1)
        dp[0] = true
        for (i in 1..input.length) {
            for (j in 0 until i) {
                val sub = input.substring(j, i)
                if (dp[j] && dictionary.contains(sub)) {
                    result.add(sub)
                    dp[i] = true
                    break
                }
            }
        }
        return dp[input.length]
    }
}