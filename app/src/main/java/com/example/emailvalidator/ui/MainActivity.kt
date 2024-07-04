package com.example.emailvalidator.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.emailvalidator.R
import com.example.emailvalidator.databinding.ActivityMainBinding
import com.example.emailvalidator.network.apiService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(apiService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.editText.value?.let { pasteText(it) }
        startUserTypingListener()
        checkEmail()
        showHint()
    }

    private fun startUserTypingListener() {
        binding.textField.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onTextChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun checkEmail() {
        binding.btnPrimary.setOnClickListener {
            viewModel.response.value.let {
                binding.tvResult.text = getString(R.string.result_text, it?.result, it?.reason)
            }
        }
    }

    private fun showHint() {
        viewModel.hint.observe(this) { text ->
            with(binding) {
                if (!text.isNullOrBlank()) {
                    tvHint.visibility = View.VISIBLE
                    tvHint.text = getString(R.string.hint_text, text)
                    tvHint.setOnClickListener {
                        pasteText(text)
                    }
                } else {
                    tvHint.visibility = View.GONE
                }
            }
        }
    }

    private fun pasteText(text: String) {
        val textHint = Editable.Factory.getInstance().newEditable(text)
        binding.textField.editText?.text = textHint
    }
}