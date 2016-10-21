package com.androidtutorialpoint.mycontacts;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class ContactsCursorAdapter extends CursorAdapter {
    public ContactsCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        return LayoutInflater.from(context).inflate(
                R.layout.contact_list_item,viewGroup,false );
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String ID = cursor.getString(cursor.getColumnIndex(DBOpenHelper.CONTACT_NAME));
        String Date = cursor.getString(cursor.getColumnIndex(DBOpenHelper.CONTACT_PHONE));
        String Type = cursor.getString(cursor.getColumnIndex(DBOpenHelper.CONTACT_PHONE));
        TextView idTextView = (TextView) view.findViewById(R.id.idTextView);
        TextView dateTextView = (TextView) view.findViewById(R.id.dateTextView);
        TextView typeTextView = (TextView) view.findViewById(R.id.typeTextView);
        idTextView.setText(ID);
        dateTextView.setText(Date);
        typeTextView.setText(Type);

    }
}
