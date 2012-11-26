package com.J_Test.httpPostTest;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener

{
	private EditText txtMessage;
	private Button sendBtn;
	// private String uriAPI = "http://192.168.1.2/httpPostTest/postTest.php";
	private String uriAPI = "http://192.168.1.2/httpPostTest/postTest_mysql.php";
	/** 「要更新版面」的訊息代碼 */
	protected static final int REFRESH_DATA = 0x00000001;

	/** 建立UI Thread使用的Handler，來接收其他Thread來的訊息 */
	Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			// 顯示網路上抓取的資料
			case REFRESH_DATA:
				String result = null;
				if (msg.obj instanceof String)
					result = (String) msg.obj;
				if (result != null)
					// 印出網路回傳的文字
					Toast.makeText(Main.this, result, Toast.LENGTH_LONG).show();
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		txtMessage = (EditText) findViewById(R.id.txt_message);
		sendBtn = (Button) findViewById(R.id.send_btn);

		if (sendBtn != null)
			sendBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)

	{
		if (v == sendBtn)
		{
			if (txtMessage != null)
			{
				// 擷取文字框上的文字
				final String msg = txtMessage.getEditableText().toString();

				// new Thread()
				// {
				// public void run()
				// {
				// String result = sendPostDataToInternet(msg);
				// mHandler.obtainMessage(REFRESH_DATA, result)
				// .sendToTarget();
				// }
				// }.start();

				// 啟動一個Thread(執行緒)，將要傳送的資料放進Runnable中，讓Thread執行
				Thread t = new Thread(new sendPostRunnable(msg));
				t.start();
			}
		}
	}

	class sendPostRunnable implements Runnable
	{
		String strTxt = null;

		// 建構子，設定要傳的字串
		public sendPostRunnable(String strTxt)
		{
			this.strTxt = strTxt;
		}

		@Override
		public void run()
		{
			String result = sendPostDataToInternet(strTxt);
			mHandler.obtainMessage(REFRESH_DATA, result).sendToTarget();
		}

	}

	private String sendPostDataToInternet(String strTxt)
	{

		/* 建立HTTP Post連線 */

		HttpPost httpRequest = new HttpPost(uriAPI);
		/*
		 * Post運作傳送變數必須用NameValuePair[]陣列儲存
		 */
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("data", strTxt));

		try

		{

			/* 發出HTTP request */

			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			/* 取得HTTP response */
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);
			/* 若狀態碼為200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200)
			{
				/* 取出回應字串 */
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());
				// 回傳回應字串
				return strResult;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
