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
            runWordBreakProblem(binding.inputEditText.text.toString(), dictionary)
            binding.resultTextview.text = getString(R.string.text_output, result.toString())
        }

        binding.bitManipulationButton.setOnClickListener {
            Intent(this, BitManipulationActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun runWordBreakProblem(input: String, dictionary: List<String>) {
        if (result.isNotEmpty()) result.clear()
        //wordBreak(input, dictionary)
        wordBreakWithRecursion(input, dictionary)
    }

    // dynamic programing solution with time complexity O(n^2) & space complexity O(n)
    private fun wordBreakWithDP(input: String, dictionary: List<String>): Boolean {
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

    // recursion solution with time complexity O(n) & space complexity O(1)
    private fun wordBreakWithRecursion(input: String, dictionary: List<String>) {
        if (input.isEmpty()) return
        for (i in 1..input.length) {
            val prefix = input.substring(0, i)
            if (dictionary.contains(prefix)) {
                result.add(prefix)
                wordBreakWithRecursion(input.substring(i), dictionary)
            }
        }
    }
}