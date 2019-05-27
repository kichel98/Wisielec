package com.example.zad2

import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var images : TypedArray
    lateinit var imageView : ImageView
    lateinit var currentImage : Drawable
    lateinit var mappedWordView : TextView
    lateinit var wordToGuess : CharArray
    lateinit var mappedWord : CharArray
    var indexOfImage = 0
    var numberOfFaults = 0
    val maxNumberOfFaults = 10
    var isGameOver = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wordToGuess = getRandomWord()
        wordToGuess = String(wordToGuess).toUpperCase().toCharArray()
        mappedWord =  CharArray(size = wordToGuess.size) {'_'}
        images = resources.obtainTypedArray(R.array.icons)
        imageView = findViewById(R.id.imageView)
        currentImage = images.getDrawable(indexOfImage)
        imageView.setImageDrawable(currentImage)
        mappedWordView = findViewById(R.id.mapped_word_view)
        mappedWordView.text = String(mappedWord)
    }

    private fun getRandomWord() : CharArray {
        val array: Array<String> = resources.getStringArray(R.array.dictionary)
        return array[Random.nextInt(array.size)].toCharArray()
    }

    fun checkGuess(view: View) {
        if(isGameOver == false) {
            val letter = (view as Button).text[0]
            if (wordToGuess.contains(letter))
                changeMap(letter)
            else
            {
                changeImage()
                numberOfFaults++
            }

            view.isEnabled = false
            checkIsOver()
        }
    }


    fun changeMap(letterToShow: Char) {
        for(i in 0..(wordToGuess.size - 1))
            if(letterToShow == wordToGuess[i])
                mappedWord[i] = letterToShow
        mappedWordView.text = String(mappedWord)
    }

    fun changeImage() {
        indexOfImage++
        currentImage = images.getDrawable(indexOfImage)
        imageView.setImageDrawable(currentImage)
    }

    fun checkIsOver() {
        if(wordToGuess contentEquals mappedWord) {
            Toast.makeText(applicationContext, "You won!", Toast.LENGTH_SHORT).show()
            isGameOver = true
            findViewById<Button>(R.id.restart).visibility = View.VISIBLE
        }
        else if(numberOfFaults == maxNumberOfFaults) {
            mappedWordView.text = String(wordToGuess)
            Toast.makeText(applicationContext, "You lost!", Toast.LENGTH_SHORT).show()
            isGameOver = true
            findViewById<Button>(R.id.restart).visibility = View.VISIBLE
        }
    }

    fun startAgain(view: View) {

        indexOfImage = 0
        numberOfFaults = 0
        isGameOver = false
        indexOfImage = 0
        currentImage = images.getDrawable(indexOfImage)
        imageView.setImageDrawable(currentImage)

        wordToGuess = getRandomWord()
        wordToGuess = String(wordToGuess).toUpperCase().toCharArray()
        mappedWord =  CharArray(size = wordToGuess.size) {'_'}
        mappedWordView.text = String(mappedWord)

        for (i in 1..32) {
            val id = resources.getIdentifier("button$i", "id", packageName)
            findViewById<Button>(id).isEnabled = true
        }

        (view as Button).visibility = View.INVISIBLE

    }

}
