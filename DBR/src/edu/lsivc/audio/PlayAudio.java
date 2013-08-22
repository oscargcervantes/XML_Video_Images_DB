package edu.lsivc.audio;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaViewer;
import com.xuggle.mediatool.ToolFactory;

/**
* Using {@link IMediaReader}, takes a media container, finds the first audio
* stream, decodes that stream, and plays the audio on your speakers.
*
* @author aclarke
* @author trebor
*/

public class PlayAudio
{

  /**
* Takes a media container (file) as the first argument, opens it, opens up a
* Swing window and displays video frames with the right
* timing.
*
* @param args
* Must contain one string which represents a filename
*/
  private String AudioFilePath;
  public PlayAudio(String AudioFile)
  {
	  AudioFilePath = AudioFile;
   }
	
  public void Play()
  {
    //if (args.length <= 0)
      //throw new IllegalArgumentException(
        //  "must pass in a filename as the first argument");

    // create a media reader for processing video

    IMediaReader reader = ToolFactory.makeReader(AudioFilePath);

    // Create a MediaViewer object and tell it to play audio only

    reader.addListener(ToolFactory.makeViewer(IMediaViewer.Mode.AUDIO_VIDEO));

    // read out the contents of the media file, and sit back and watch

    while (reader.readPacket() == null)
      do {} while(false);
  }
}


