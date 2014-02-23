/**
 * @author QSY
 * ���ܣ������ߺ����ؼ���BMI�����BMI���Ա�����Ӧ�Ľ��飬
 * 		�û�����˵��й���ѡ�����ʾ������Ϣ������˵����˳�ѡ����˳������
 */
//�������
package com.twlkyao.bmi;
//������Ӧ�İ�
import java.text.DecimalFormat;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//�̳и���Activity
public class BMIActivity extends Activity 
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {//��������
        super.onCreate(savedInstanceState);	//��д�����onCreate����
        setContentView(R.layout.main);	//���õ�ǰActivity�Ĳ����ļ�Ϊmain.xml
        findViews();	//����findViews����
        setListeners();	//����setListeners����
    };
    
	private Button button_cal;	//����˽�е�Button����button_cal�����ڴ�������
	private Button button_share;	//����˽�е�Button����button_share�����ڴ���΢������
	private EditText fieldheight;	//����˽�е�EditText����fieldheight�����ڼ�¼���
	private EditText fieldweight;	//����˽�е�EditText����fieldweight�����ڼ�¼����
	private EditText fieldsex;	//����˽�е�EditText����fieldsex�����ڼ�¼�Ա�
	private TextView result;	//����˽�е�TextView����result��������ʾ���
	private TextView suggest;	//����˽�е�TextView����suggest��������ʾ����
	
	//ͨ��ID���ҿؼ���ͳһ�ɺ���
	private void findViews()
	{
		button_cal=(Button)findViewById(R.id.btn_cal);	//ͨ��ID�ҵ����㰴ť
		button_share=(Button)findViewById(R.id.btn_share);	//ͨ��ID�ҵ����?ť
		fieldheight=(EditText)findViewById(R.id.height);	//ͨ��ID�ҵ�height�ؼ�
		fieldweight=(EditText)findViewById(R.id.weight);	//ͨ��ID�ҵ�weight�ؼ�
		fieldsex=(EditText)findViewById(R.id.sex);	//ͨ��ID�ҵ�sex�ؼ�
		result=(TextView)findViewById(R.id.result);	//ͨ��ID�ҵ����ؼ�
		suggest=(TextView)findViewById(R.id.suggest);	//ͨ��ID�ҵ�����ؼ�
	};
	
	//�����
	private void setListeners()
	{
		button_cal.setOnClickListener(calcBMI);//��������������ڼ��㰴ť������
		button_share.setOnClickListener(shareResult);	//��������������ڷ��?ť������
		
	};
	
	//���ͻ����MENU����ʱ�����ø÷���
    @Override
	public boolean onCreateOptionsMenu(Menu menu) //���ص�����д����
    {
		// TODO Auto-generated method stub
    	menu.add(0, 1, 1, R.string.about);	//��ӹ��ڲ˵�
    	menu.add(0, 2, 2, R.string.exit);	//����˳��˵�
		return super.onCreateOptionsMenu(menu);
	};

    //���û�����˵��е�ĳ��ѡ��ʱ�����ø÷���
	@Override
	public boolean onOptionsItemSelected(MenuItem item)	//���ص�����д���� 
	{
		// TODO Auto-generated method stub
		if(2==item.getItemId())	//����˳�
		{
			finish();	//����finish�������˳�����
		}
		else if(1==item.getItemId())	//�������
		{
			openOptionsDialog();	//����Toast����Ԫ����ʾ��Ϣ
		}
		return super.onOptionsItemSelected(item);
	};
	
	//���㰴�����ʵ��
	private Button.OnClickListener calcBMI=new Button.OnClickListener()
	{
		public void onClick(View v)
		{
			DecimalFormat df=new DecimalFormat("0.00");	//���ü�������ʾ��ʽ
			double height=Double.parseDouble(fieldheight.getText().toString())/100;//�õ���ߣ���λת��Ϊm
			double weight=Double.parseDouble(fieldweight.getText().toString());//�õ�����
			String sex=fieldsex.getText().toString();//�õ��Ա�
			double BMI =weight/(height*height);	//����BMI
			
			//���BMI��ֵ�����ռ���
			result.setText(getString(R.string.result)+df.format(BMI));
			
			//����Ա��BMI�������
			//Ů��
			if(sex.equals("女"))
			{
				if(BMI>29)
				{
					suggest.setText(R.string.advice_fat_female);			
				}
				else if(BMI>24)
				{
					suggest.setText(R.string.advice_heavy_female);
				}
				else if(BMI>18)
				{
					suggest.setText(R.string.advice_ok_female);
				}
				else if(BMI<18)
				{
					suggest.setText(R.string.advice_light_female);
				}
			}
			//����
			else if(sex.equals("男"))
			{
				if(BMI>30)
				{
					suggest.setText(R.string.advice_fat_male);			
				}
				else if(BMI>25)
				{
					suggest.setText(R.string.advice_heavy_male);
				}
				else if(BMI>20)
				{
					suggest.setText(R.string.advice_ok_male);
				}
				else if(BMI<20)
				{
					suggest.setText(R.string.advice_light_male);
				}
			}
		}
	};
	
	//������?��
	//ԭ����Ƿ�����Ӧ��intent��ϵͳ������InterFilter�ҵ��������������������google��android��intent���ơ�
	private Button.OnClickListener shareResult=new Button.OnClickListener()
	{
		public void onClick(View v)
		{
			Intent intent=new Intent(Intent.ACTION_SEND);  
			intent.setType("text/plain");  //������������
			intent.putExtra(Intent.EXTRA_SUBJECT,"BMI" );  //����
			
			intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_result)
					+result.getText()+getResources().getString(R.string.share_suggest)
						+suggest.getText()
							+getResources().getString(R.string.share_invite));  //���������
			
			startActivity(Intent.createChooser(intent, 
					getResources().getString(R.string.share_title)));  //Ŀ��Ӧ��ѡ��Ի���ı���
		}
	};
	
	//��ʾToast�Ի������
	private void openOptionsDialog()
	{
		//����Toast����Ԫ����ʾ�����Ϣ����ʾʱ��Ϊ��
		//��BMIActivity����ʾR.string.about_msg�е���Ϣ����ʾʱ��Ϊ��
		Toast.makeText(BMIActivity.this,R.string.about_msg,Toast.LENGTH_SHORT).show();		
	}
}



/*
Intent intent=new Intent(Intent.ACTION_SEND);  
intent.setType("text/plain");  //������������
intent.putExtra(Intent.EXTRA_SUBJECT, "subject");  //����
intent.putExtra(Intent.EXTRA_TEXT,  "content");  //����
startActivity(Intent.createChooser(intent, "title"));  //Ŀ��Ӧ��ѡ��Ի���ı���
*/
