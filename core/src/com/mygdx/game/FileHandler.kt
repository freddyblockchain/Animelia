package com.mygdx.game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import java.io.BufferedWriter

class FileHandler {
    companion object{
        private lateinit var fileWriter: BufferedWriter
        fun writeToFile(entityId : Int,content: String){
          /*  val lines = readFromFile().toMutableList()
            val existingLineIndex = lines.indexOfFirst{ it.contains("\"entityId\":$entityId") }
            if (existingLineIndex == -1){
                lines.add(content)
            }else{
                lines[existingLineIndex] = content
            }
            //fileWriter = file.bufferedWriter()
           // fileWriter.use { writer -> lines.forEach { writer.write(it)
             //   writer.newLine()}}*/
        }

        fun getFileJson(fileName: String): String{
            val handle: FileHandle = Gdx.files.internal(fileName);
            return handle.file().readText()
        }

        /*fun readFromFile(): List<String>{
            return file.useLines { it.toList() }
        }*/
    }
}
