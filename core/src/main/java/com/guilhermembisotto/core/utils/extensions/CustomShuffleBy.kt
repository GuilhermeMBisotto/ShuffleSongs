package com.guilhermembisotto.core.utils.extensions

import java.util.Collections

private class CustomShuffleBy<T, K> {

    private val shuffledList = Collections.synchronizedList(mutableListOf<T>())
    private val originalList = Collections.synchronizedList(mutableListOf<T>())
    private var originalListSize = 0
    private var countOfTries = 0
    private lateinit var keySelectorShuffleBy: (T) -> K

    fun customShuffle(list: List<T>, shuffleBy: (T) -> K): MutableList<T> {
        shuffledList.clear()
        originalListSize = list.size
        originalList.addAll(list)
        keySelectorShuffleBy = shuffleBy

        recursiveShuffled()

        return shuffledList
    }

    private fun recursiveShuffled() {
        if (countOfTries == originalListSize) {
            shuffledList.addAll(originalList.shuffled())
            originalList.clear()
        } else {
            val rand = originalList.random()

            if (shuffledList.isNullOrEmpty()) {
                shuffledList.add(rand)
                originalList.remove(rand)

                countOfTries = 0
            } else {
                when {
                    keySelectorShuffleBy(shuffledList.last()) != keySelectorShuffleBy(rand) -> {
                        shuffledList.add(rand)
                        originalList.remove(rand)

                        countOfTries = 0
                    }
                    keySelectorShuffleBy(shuffledList.first()) != keySelectorShuffleBy(rand) -> {
                        shuffledList.add(0, rand)
                        originalList.remove(rand)

                        countOfTries = 0
                    }
                    else -> tryToInsetInMiddle(rand)
                }
            }
        }

        if (!originalList.isNullOrEmpty()) {
            countOfTries++

            recursiveShuffled()
        }
    }

    private fun tryToInsetInMiddle(obj: T) {
        var indexToChange = -1

        with(shuffledList.iterator().withIndex()) {
            forEach {
                if (it.index + 1 < shuffledList.size) {
                    if (shuffledList[it.index + 1] != null) {
                        if (keySelectorShuffleBy(shuffledList[it.index + 1]) !=
                            keySelectorShuffleBy(obj) &&
                            keySelectorShuffleBy(it.value) != keySelectorShuffleBy(obj)
                        ) {
                            indexToChange = it.index
                        }
                    }
                }
            }
        }

        if (indexToChange != -1) {
            shuffledList.add(indexToChange + 1, obj)
            originalList.remove(obj)

            countOfTries = 0
        }
    }
}

fun <T, K> List<T>.customShuffle(shuffleBy: (T) -> K): List<T> =
    CustomShuffleBy<T, K>().customShuffle(
        this,
        shuffleBy
    )
