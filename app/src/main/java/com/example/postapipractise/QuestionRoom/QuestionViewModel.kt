package com.example.postapipractise.QuestionRoom

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuestionViewModel(application: Application) : AndroidViewModel(application) {

    private val questionDao = QuestionDataBase.getInstance(application).questionDao()

    private val _questions = MutableLiveData<List<Question>>()
    val questions: LiveData<List<Question>>
        get() = _questions

    fun loadQuestions() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val questions = questionDao.getAllQuestions()
                val nestedQuestions = loadNestedQuestions(questions)
                _questions.postValue(nestedQuestions)
            }
        }
    }

    private fun loadNestedQuestions(questions: List<Question>): List<Question> {
        return questions.map { question ->
            val subQuestions = question.subQuestions.map { subQuestion ->
                val nestedSubQuestions = subQuestion.subQuestions?.let { loadNestedSubQuestions(it) }
                subQuestion.copy(subQuestions = nestedSubQuestions ?: emptyList())
            }
            question.copy(subQuestions = subQuestions)
        }
    }

    private fun loadNestedSubQuestions(subQuestions: List<SubQuestion>): List<SubQuestion> {
        return subQuestions.map { subQuestion ->
            val nestedSubQuestions = subQuestion.subQuestions?.let { loadNestedSubQuestions(it) }
            subQuestion.copy(subQuestions = nestedSubQuestions ?: emptyList())
        }
    }

    fun insertQuestions(questions: List<Question>) {
        viewModelScope.launch(Dispatchers.IO) {
            questionDao.insertAll(questions)
        }
    }


}

