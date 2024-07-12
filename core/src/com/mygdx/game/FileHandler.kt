package com.mygdx.game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.mygdx.game.Signal.Signal
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.io.BufferedWriter
import java.io.File

class FileHandler {
    companion object{

        var handle = Gdx.files.internal("assets/SaveFiles/CurrentSave")
        private val file: File = handle.file()
        private lateinit var fileWriter: BufferedWriter
        fun savePlayerState(playerState: String){
            val lines = readFromFile().toMutableList()
            if (lines.isEmpty()){
                lines.add(playerState)
            }else{
                lines[0] = playerState
            }
            fileWriter = file.bufferedWriter()
            fileWriter.use { writer -> lines.forEach { writer.write(it)
                writer.newLine()}}
        }
        fun readFromFile(): List<String>{
            return file.useLines { it.toList() }
        }

        fun getFileJson(fileName: String): String{
            val handle: FileHandle = Gdx.files.internal(fileName);
            return handle.file().readText()
        }

        fun SaveFileEmpty(): Boolean{
            return file.length() == 0L
        }
        fun writeSignalToFile(signal: Signal){
            val lines = readFromFile().toMutableList()
            val serializer = serializer(signal::class.java)
            val signalContent = Json.encodeToString(serializer,signal)
            lines.add(signalContent)
            fileWriter = file.bufferedWriter()
            fileWriter.use { writer -> lines.forEach { writer.write(it)
                writer.newLine()}}
        }
    }
}
