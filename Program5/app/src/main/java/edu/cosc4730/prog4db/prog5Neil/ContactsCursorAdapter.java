package edu.cosc4730.prog4db.prog5Neil;

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
        String contactName = cursor.getString(cursor.getColumnIndex(DBOpenHelper.KEY_NAME));
        String contactDate = cursor.getString(cursor.getColumnIndex(DBOpenHelper.KEY_DATE));
        String contactType = cursor.getString(cursor.getColumnIndex(DBOpenHelper.KEY_TYPE));
        String contactAmount= cursor.getString(cursor.getColumnIndex(DBOpenHelper.KEY_AMOUNT));
        TextView nameTextView = (TextView) view.findViewById(R.id.nameTextView);
        TextView dateTextView = (TextView) view.findViewById(R.id.dateTextView);
        TextView typeTextView = (TextView) view.findViewById(R.id.typeTextView);
        TextView amountTextView = (TextView) view.findViewById(R.id.amountTextView);
        nameTextView.setText("Name: " + contactName);
        dateTextView.setText("Date: " + contactDate);
        typeTextView.setText("Type: " + contactType);
        amountTextView.setText("Amount: " + contactAmount);

    }
}
