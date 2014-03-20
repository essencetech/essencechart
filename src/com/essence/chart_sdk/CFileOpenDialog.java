package com.essence.chart_sdk;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

class OpenDialogLayout extends LinearLayout {

	public OpenDialogLayout(Context context) {
		super(context);

		init(context);
	}

	public OpenDialogLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

		init(context);
	}

	private void setItemLayout(View view) {
		view.setLayoutParams(new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 0.0F));
	}

	private void setListLayout(View view) {
		view.setLayoutParams(new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT, 0.0F));
	}

	private void init(Context context) {
		setOrientation(LinearLayout.VERTICAL);
		setListLayout(this);

		_tvPath = new TextView(context);
		setItemLayout(_tvPath);
		_tvPath.setText("Path: ");

		_etFile = new EditText(context);
		setItemLayout(_etFile);
		_etFile.setEnabled(false);
		_etFile.setFocusable(false);

		_FileList = new FileList(context);
		setListLayout(_FileList);
		_FileList.setPath("/");
		_FileList.setFocusable(true);
		_FileList.setOnPathChangedListener(_OnPathChanged);
		_FileList.setOnFileSelected(_OnFileSelected);

		addView(_tvPath);
		addView(_etFile);
		addView(_FileList);

		setFocusable(true);
		setFocusableInTouchMode(true);
	}

	public TextView _tvPath = null;
	public EditText _etFile = null;
	public FileList _FileList = null;

	public String getPath() {
		return _tvPath.getText().toString();
	}

	public String getFileName() {
		return _etFile.getText().toString();
	}

	private OnPathChangedListener _OnPathChanged = new OnPathChangedListener() {
		public void onChanged(String path) {
			_tvPath.setText("	/mnt" + path);
			_etFile.setText("");
		}
	};

	private OnFileSelectedListener _OnFileSelected = new OnFileSelectedListener() {
		public void onSelected(String path, String fileName) {
			_etFile.setText(fileName);
		}
	};

}

public class CFileOpenDialog {
	Context m_pParent = null;
	
	public CFileOpenDialog(Context context) {
		_OpenDialogLayout = new OpenDialogLayout(context);

		_Dialog = new AlertDialog.Builder(context);
		_Dialog.setTitle(context.getString(R.string.chart_title_fileopen));
		_Dialog.setView(_OpenDialogLayout);
		_Dialog.setPositiveButton(context.getString(R.string.chart_property_ok), _OnPositiveClick);
		_Dialog.setNegativeButton(context.getString(R.string.chart_property_cancel), _OnNegativeClick);
	}
	

	private Builder _Dialog = null;
	private OpenDialogLayout _OpenDialogLayout = null;
	
	private OnFileSelectedListener _OnFileSelected = null;
	private OnNotifyEventListener _OnCanceled = null;

	public AlertDialog Show() {
		return _Dialog.show();
	}
	
	public void setOnFileSelected(OnFileSelectedListener value) {
		_OnFileSelected = value;
	}

	public OnFileSelectedListener getOnFileSelected() {
		return _OnFileSelected;
	}

	public void setOnCanceled(OnNotifyEventListener value) {
		_OnCanceled = value;
	}

	public OnNotifyEventListener getOnCanceled() {
		return _OnCanceled;
	}

	private DialogInterface.OnClickListener _OnPositiveClick = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			if (_OnFileSelected != null) {
				_OnFileSelected.onSelected(_OpenDialogLayout.getPath(),
						_OpenDialogLayout.getFileName());
			}
		}
	};

	private DialogInterface.OnClickListener _OnNegativeClick = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			if (_OnCanceled != null) {
				_OnCanceled.onNotify(CFileOpenDialog.this);
			}
		}
	};
	
	public void SetPropertyType(int nPropertyType, int nId)
	{
	}
}

interface OnFileSelectedListener {

	public void onSelected(String path, String fileName);

}

interface OnNotifyEventListener {

	public void onNotify(Object sender);

}

interface OnPathChangedListener {

	public void onChanged(String path);

}

class FileList extends ListView {

	public FileList(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		init(context);
	}

	public FileList(Context context, AttributeSet attrs) {
		super(context, attrs);

		init(context);
	}

	public FileList(Context context) {
		super(context);

		init(context);
	}

	private void init(Context context) {
		_Context = context;
		setOnItemClickListener(_OnItemClick);
	}

	private Context _Context = null;
	private ArrayList<String> _List = new ArrayList<String>();
	private ArrayList<String> _FolderList = new ArrayList<String>();
	private ArrayList<String> _FileList = new ArrayList<String>();
	private ArrayAdapter<String> _Adapter = null;

	private String _Path = "";

	private OnPathChangedListener _OnPathChangedListener = null;
	private OnFileSelectedListener _OnFileSelectedListener = null;

	private boolean openPath(String path) {
		_FolderList.clear();
		_FileList.clear();

		File file = new File(path);
		File[] files = file.listFiles();
		if (files == null)
			return false;

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				_FolderList.add("<" + files[i].getName() + ">");
			} else {
				String strName = files[i].getName();
				if (strName.length() <= 4)
				{
					continue;
				}
				String strExt = strName.substring(strName.length() - 4);
				if (0 == strExt.compareToIgnoreCase(".png"))
				{
					_FileList.add(files[i].getName());
				}
			}
		}

		Collections.sort(_FolderList);
		Collections.sort(_FileList);

		_FolderList.add(0, "<..>");

		return true;
	}

	private void updateAdapter() {
		_List.clear();
		_List.addAll(_FolderList);
		_List.addAll(_FileList);

		_Adapter = new ArrayAdapter<String>(_Context,
				android.R.layout.simple_list_item_1, _List);
		setAdapter(_Adapter);
	}

	public void setPath(String value) {
		if (value.length() == 0) {
			value = "/";
		} else {
			String lastChar = value.substring(value.length() - 1,
					value.length());
			if (lastChar.matches("/") == false)
				value = value + "/";
		}

		if (openPath(value)) {
			_Path = value;
			updateAdapter();
			if (_OnPathChangedListener != null)
				_OnPathChangedListener.onChanged(value);
		}
	}

	public String getPath() {
		return _Path;
	}

	public void setOnPathChangedListener(OnPathChangedListener value) {
		_OnPathChangedListener = value;
	}

	public OnPathChangedListener getOnPathChangedListener() {
		return _OnPathChangedListener;
	}

	public void setOnFileSelected(OnFileSelectedListener value) {
		_OnFileSelectedListener = value;
	}

	public OnFileSelectedListener getOnFileSelected() {
		return _OnFileSelectedListener;
	}

	public String DelteRight(String value, String border) {
		String list[] = value.split(border);

		String result = "";

		for (int i = 0; i < list.length; i++) {
			result = result + list[i] + border;
		}

		return result;
	}

	private String delteLastFolder(String value) {
		String list[] = value.split("/");

		String result = "";

		for (int i = 0; i < list.length - 1; i++) {
			result = result + list[i] + "/";
		}

		return result;
	}

	private String getRealPathName(String newPath) {
		String path = newPath.substring(1, newPath.length() - 1);

		if (path.matches("..")) {
			return delteLastFolder(_Path);
		} else {
			return _Path + path + "/";
		}
	}

	private AdapterView.OnItemClickListener _OnItemClick = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long id) {
			String fileName = getItemAtPosition(position).toString();
			if (fileName.matches("<.*>")) {
				setPath(getRealPathName(fileName));
			} else {
				if (_OnFileSelectedListener != null)
					_OnFileSelectedListener.onSelected(_Path, fileName);
			}
		}
	};

}
