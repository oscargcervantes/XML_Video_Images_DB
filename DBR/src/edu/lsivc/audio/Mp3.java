package edu.lsivc.audio;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.*;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;


public class Mp3 
{
   private MP3File f;
   private static File file;
   public Mp3(File f)
   {
     file = f;
   }
   
   public void GetMetadata()
   {
	   AudioFile f;
		try {
			 f = new MP3File(file);
			 Tag tag = f.getTag();
			 MP3AudioHeader mp3audio = (MP3AudioHeader) f.getAudioHeader();
		     int length = f.getAudioHeader().getTrackLength();
		     int samplerate = f.getAudioHeader().getSampleRateAsNumber();
		     String AlbumArtist = tag.getFirst(FieldKey.ALBUM_ARTIST);
		     String Artist = tag.getFirst(FieldKey.ARTIST);
		     String Composer = tag.getFirst(FieldKey.COMPOSER);
		     String Language = tag.getFirst(FieldKey.LANGUAGE);
		     String Album = tag.getFirst(FieldKey.ALBUM);
		     String Comment = tag.getFirst(FieldKey.COMMENT);
		     System.out.println("Album Artist:" + AlbumArtist);
		     System.out.println("Artist:" + Artist);
		     System.out.println("Composer:" + Composer);
		     System.out.println("Language:" + Language);
		     System.out.println("Comment:" + Comment);
		     System.out.println("Length: " + length + " SampleRate: " + samplerate + " " + Album);
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ReadOnlyFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAudioFrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
   }
   
   public static void main(String[] args)
   {
	   File file = new File("/home/oscargcervantes/Downloads/EC350.mp3");
	   Mp3 test = new Mp3(file);
	   test.GetMetadata();
   }
}

