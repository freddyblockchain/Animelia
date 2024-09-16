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

        lateinit var signalHandle: FileHandle
        lateinit var playerHandle: FileHandle
        private lateinit var signalFile: File
        private lateinit var playerFile: File
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
            val handle: FileHandle = Gdx.files.internal(fileName)
            return handle.readString()
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
        fun clearSaves(){
            signalFile.writeText("")
            playerFile.writeText("")
        }

        fun initSaveFiles(){
            signalHandle = Gdx.files.local("SaveFiles/SignalSave")
            playerHandle =  Gdx.files.local("SaveFiles/PlayerSave")
            // Ensure the directory exists
            signalHandle.file().parentFile?.mkdirs()
            playerHandle.file().parentFile?.mkdirs()

            // Check if the files exist, and create them if they don't
            if (!signalHandle.exists()) {
                signalHandle.file().createNewFile()
            }
            if (!playerHandle.exists()) {
                playerHandle.file().createNewFile()
            }
            signalFile = signalHandle.file()
            playerFile  = playerHandle.file()
        }

        val BASE_PATH = if (Gdx.files.internal("assets/").exists()) {
            "assets/"
        } else {
            ""
        }

    }
}
