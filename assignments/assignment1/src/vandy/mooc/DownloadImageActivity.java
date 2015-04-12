package vandy.mooc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

/**
 * An Activity that downloads an image, stores it in a local file on
 * the local device, and returns a Uri to the image file.
 */
public class DownloadImageActivity extends Activity {
    /**
     * Debugging tag used by the Android logger.
     */
    private final String TAG = getClass().getSimpleName();

    /**
     * Hook method called when a new instance of Activity is created.
     * One time initialization code goes here, e.g., UI layout and
     * some class scope variable initialization.
     *
     * @param savedInstanceState object that contains saved state information.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Always call super class for necessary
        // initialization/implementation.
        // @@ TODO -- you fill in here.
    	super.onCreate(savedInstanceState);

        // Get the URL associated with the Intent data.
        // @@ TODO -- you fill in here.
    	Bundle extras = getIntent().getExtras();
        
        final String mURL=extras.getString("URL");
        Log.i(TAG,"en downloader - extra:"+mURL);
        
    	

        // Download the image in the background, create an Intent that
        // contains the path to the image file, and set this as the
        // result of the Activity.
        myRunnable a = new myRunnable(mURL);
        Thread mThread = new Thread(a);
     	mThread.start();

        // @@ TODO -- you fill in here using the Android "HaMeR"
        // concurrency framework.  Note that the finish() method
        // should be called in the UI thread, whereas the other
        // methods should be called in the background thread.  See
        // http://stackoverflow.com/questions/20412871/is-it-safe-to-finish-an-android-activity-from-a-background-thread
        // for more discussion about this topic.
     	return;
		
    }

    class myRunnable implements Runnable {

    	String myUrl;
    	myRunnable(String url)
    	{
    		myUrl=url;
    	}
    	@Override
    	public void run() {
   	      //download image from url/file
   		   final Uri imagePath = DownloadUtils.downloadImage(getBaseContext(), Uri.parse(myUrl));
   		   Intent feedback=new Intent();
	        if(imagePath==null)
	        	setResult(RESULT_CANCELED,feedback);
	        else{
	   	    	feedback.putExtra("PATH", imagePath.toString());
	   	    	Log.i(TAG,"Image Path: "+imagePath);
	   	    	setResult(RESULT_OK,feedback);
	        }
   		   //runOnUiThread to return result to main activity
   		   runOnUiThread(new Runnable() {
           	   @Override
           	   public void run() {
           	    	finish(); //Aqui revisar lo del foro de stackover
           	   }
   		   });

			
		}
    }
}
