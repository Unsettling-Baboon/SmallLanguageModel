import java.io.File
import kotlin.random.Random

fun main() {
    countWords("Rasika has a cat. Ellen has a cat.")
    countCharNGrams("Rasika has a cat. Ellen has a cat.")
    nextLetterFrequency("a ", countCharNGrams("Rasika has a cat. Ellen has a cat. Miguel has a dog.", 4))
    generateNextChar("a ", countCharNGrams("Rasika has a cat. Ellen has a cat. Miguel has a dog.", 4))
    generateNextCharTesting()
}

/* Question 1

Write a function fun countWords(text: String): Map<String, Int> which
counts the number of times that each word appears in the supplied String
text. For example, if the input text is "Rasika has a cat. Ellen has a
cat.", it should output the counts in a map formatted like this:
{Rasika=1, has=2, a=2, cat.=2, Ellen=1} (the keys are the words that
appear in the text, and the values are the number of times that each word
appears). To obtain the words in the String, you may split it using a
space as the delimiter (ignoring the issues with punctuation). */

fun countWords(text: String): Map<String, Int> {
    val count = mutableMapOf<String, Int>()
        for (word in text.split(" ") /* remember, .split() is a
                                                   method in the String
                                                   class that splits a string
                                                   based on certain delimiters
                                                   , like spaces! */) {

        count[word] = count.getOrDefault(word, 0) + 1
        /* the above thing is how you can ADD a NEW PAIR to a map; you
           simply say that the value for a given key is something ELSE,
           and it automatically generates a pair in that map with that
           key and value!!! */

        /* count.put(word, if (count[word] == null) 1 else (count[word]!! + 1))
           above is the more roundabout way of doing it that you found out */
    }
    /*println(count)*/
    return count
}

/* Question 2

When we process text in machine learning, we often use character ngrams
instead of entire words, to avoid dealing with punctuation and typos.
For example, the text "Rasika", with a value of n=4, has these character
ngrams: "Rasi", "asik", and "sika". Each of these character ngrams appears
once in "Rasika", and each of them appears twice in "RasikaRasika" (and
"RasikaRasika" also has the additional character ngrams "ikaR", "kaRa",
and "aRas", each of which appears once).

Write a function fun countCharNGrams(text: String, n: Int = 3): Map<String, Int>
which works very similarly to countWords(), but instead of counting entire
words, it counts each sequence of n characters. This function will look very
similar to countWords(). The main difference will be that instead of splitting
the text using a space, you will iterate through the characters of the String
and use the appropriate indices to build substrings which are character ngrams.
Note the default value of the parameter n. */

fun countCharNGrams(text: String, n: Int = 3): Map<String, Int> {
    val count = mutableMapOf<String, Int>()
    for (i in 0..text.length - n) {
        val nGram = text.substring(i, i + n)
        count[nGram] = count.getOrDefault(nGram, 0) + 1
    }
    /*println(count)*/
    return count
}

/* Question 3

Write a function
fun nextLetterFrequency(prompt: String, counts: Map<String, Int>): Map<Char, Int>.
This question will take the map built in countCharNGrams() as a parameter to calculate
the possibilities for the next letter given the previous n-1 letters (from the prompt
String). You can assume that the parameter prompt has length n-1, and counts has the
format of the map output by countCharNGrams().

This function should return a map where the keys are the options for the nth character
(given the previous n-1 characters), and the values are the number of times that that
character is the nth character in counts. For example, in the String "Rasika has a cat.
Ellen has a cat. Miguel has a dog." with n=4, we know that countCharNGrams() should
have included that "a c" appears twice and "a d" appears once. So,
nextLetterFrequency("a ", counts) should return the map {c=2, d=1} because, out of all
the times that "a *" appears, * is c twice and * is d once. */

fun nextLetterFrequency(prompt: String, counts: Map<String, Int>): Map<Char, Int> {
    /* Initialize an empty map to store the next letter frequencies.
       Loop through each entry (ngram, count) in the counts map.
       Check if the ngram starts with the prompt.
       Get the next character after the prompt in the ngram.
       Update the frequency of this nextChar in nextCharCounts.
       Return the Map. */

    val newCount = mutableMapOf<Char, Int>()
    for ((ngram, count) in counts) {
        if(ngram.startsWith(prompt)) {
            val nextChar = ngram[prompt.length]
            newCount[nextChar] = count
        }
    }
    /*println(newCount)*/
    return newCount
}

/* Question 4

Write a function fun generateNextChar(prompt: String, counts: Map<String, Int>): Char.
This function will call nextLetterFrequency() to get the options for the next character
given the prompt (which you can assume has length n-1), and then randomly choose one
of the characters and return it. You can also assume that counts has the format output
by countCharNGrams().

This function should first call nextLetterFrequency(). If the map returned is empty
(because the character ngram prompt was not there in the original text), this function
should randomly choose a letter between a and z, and return it. If the map returned is
not empty, then this function should randomly choose one of the characters which is
listed as an option in the map, and return it.

You may test out this function by calling it in a for loop and printing the letters one
by one:

val nGramCounts = countCharNGrams(text, 4)
var prompt = "ras"
print(prompt)
for (i in 1..20) {
    val nextLetter = generateNextChar(prompt, nGramCounts)
    print(nextLetter)
    prompt = prompt.substring(1) + nextLetter
}

If you are looking for an added challenge, use the counts returned by nextLetterFrequency()
to indicate how likely each next letter should be. For example, if nextLetterFrequency()
returns the map {c=2, d=1} then generateNextChar() should be twice as likely to generate
c as d. You can do this by generating a random number between 0 and 3. If the number is 0
or 1, then return c, and if the number is 2, then return d. This is an added challenge,
and it is expected that if you attempt it, this lab will take longer than the amount of
time available in class. */

fun generateNextChar(prompt: String, counts: Map<String, Int>): Char {
    val frequency = nextLetterFrequency(prompt, counts)
    val weightedList = mutableListOf<Char>()
    if (frequency.isEmpty()) {
        when (Random.nextInt(26)) {
            0 -> return 'a'
            1 -> return 'b'
            2 -> return 'c'
            3 -> return 'd'
            4 -> return 'e'
            5 -> return 'f'
            6 -> return 'g'
            7 -> return 'h'
            8 -> return 'i'
            9 -> return 'j'
            10 -> return 'k'
            11 -> return 'l'
            12 -> return 'm'
            13 -> return 'n'
            14 -> return 'o'
            15 -> return 'p'
            16 -> return 'q'
            17 -> return 'r'
            18 -> return 's'
            19 -> return 't'
            20 -> return 'u'
            21 -> return 'v'
            22 -> return 'w'
            23 -> return 'x'
            24 -> return 'y'
            25 -> return 'z'
            else -> return ' '
        }
    } else {
        /* SIMPLER WAY:

           println(frequency.keys.random())
           frequency.keys.random()*/

        for ((character, times) in frequency) {
            for (i in 0..<times) {
                weightedList.add(character)
            }
        }
        return weightedList.random()
    }
}

/* Question 5

If you got to this question, it is likely that you are working outside of lab time.
Questions 2, 3, and 4 together comprise a small language model. Time to have it
read some song lyrics, and have it generate songs!

You can read a text file into a String like this:
val text: String = File("path/to/file.txt").bufferedReader().use { it.readText() }
where "path/to/file.txt" is replaced by the path to a text file. Here is a dataset
of Olivia Rodrigo song lyrics. {Links to an external site.} You may also use any
dataset of song lyrics that you would like. A longer file containing a large number
of song lyrics will be better (since it will contain more options for character
ngrams).

Once you have a String containing a large amount of song lyrics text, use the for
loop (from testing out Q4) to generate a bunch of characters! */

fun generateNextCharTesting() {
    val text: String = File("/Users/abhicado/Downloads/Olivia_Rodrigo_Songs/" +
            "GUTS/ballad_of_a_homeschooled_girl.txt").bufferedReader().use { it.readText() }
    val nGramCounts = countCharNGrams(text, 4)
    var prompt = "hal"
    print(prompt)
    for (i in 1..200) {
        val nextLetter = generateNextChar(prompt, nGramCounts)
        print(nextLetter)
        prompt = prompt.substring(1) + nextLetter
    }
}