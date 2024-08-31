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

        var signalHandle = Gdx.files.internal("assets/SaveFiles/SignalSave")
        var playerHandle =  Gdx.files.internal("assets/SaveFiles/PlayerSave")
        private val signalFile: File = signalHandle.file()
        private val playerFile: File = playerHandle.file()
        private lateinit var fileWriter: BufferedWriter
        fun savePlayerState(playerState: String){
            val lines = readPlayerFile().toMutableList()
            if (lines.isEmpty()){
                lines.add(playerState)
            }else{
                lines[0] = playerState
            }
            fileWriter = playerFile.bufferedWriter()
            fileWriter.use { writer -> lines.forEach { writer.write(it)
                writer.newLine()}}
        }
        fun readSignalFile(): List<String>{
            return signalFile.useLines { it.toList() }
        }

        fun readPlayerFile(): List<String>{
            return playerFile.useLines { it.toList() }
        }

        fun getFileJson(fileName: String): String{
            val handle: FileHandle = Gdx.files.internal(fileName);
            return handle.file().readText()
        }

        fun SaveFileEmpty(): Boolean{
            return playerFile.length() == 0L
        }
        fun writeSignalToFile(signal: Signal){
            val lines = readSignalFile().toMutableList()
            val serializer = serializer(signal::class.java)
            val signalContent = Json.encodeToString(serializer,signal)
            lines.add(signalContent)
            fileWriter = signalFile.bufferedWriter()
            fileWriter.use { writer -> lines.forEach { writer.write(it)
                writer.newLine()}}
        }
    }
}
