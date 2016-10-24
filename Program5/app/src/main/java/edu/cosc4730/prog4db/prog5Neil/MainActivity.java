package edu.cosc4730.prog4db.prog5Neil;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private CursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cursorAdapter = new ContactsCursorAdapter(this, null, 0);

        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(cursorAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getLoaderManager().initLoader(0, null, this);
        FloatingActionButton floatactionbutton = (FloatingActionButton) findViewById(R.id.floatactionbutton);
        floatactionbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                LayoutInflater li = LayoutInflater.from(MainActivity.this);
                View getEmpIdView = li.inflate(R.layout.popup_dialog, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(getEmpIdView);
                //builder.setTitle("Enter Information");

                final EditText nameInput = (EditText) getEmpIdView.findViewById(R.id.editTextDialogNameInput);
                final EditText dateInput = (EditText) getEmpIdView.findViewById(R.id.editTextDialogDateInput);
                final EditText typeInput = (EditText) getEmpIdView.findViewById(R.id.editTextDialogTypeInput);
                final EditText amountInput = (EditText) getEmpIdView.findViewById(R.id.editTextDialogAmountInput);
                // set popup_dialog message

                builder.setCancelable(false).setPositiveButton("Add", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        insertContact(nameInput.getText().toString(), dateInput.getText().toString(), typeInput.getText().toString(), amountInput.getText().toString());
                        restartLoader();

                    }
                });

                builder.setNegativeButton("Cancel ", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        //Do Nothing Here
                    }
                });

                builder.setCancelable(false).setNeutralButton("Delete", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                                deleteContact(nameInput.getText().toString());
                                restartLoader();

                    }
                }).create().show();

            }
        });
    }


    private void insertContact(String Name, String Date, String CheckNum, String Amount)
    {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.KEY_NAME,Name);
        values.put(DBOpenHelper.KEY_DATE,Date);
        values.put(DBOpenHelper.KEY_TYPE,CheckNum);
        values.put(DBOpenHelper.KEY_AMOUNT,Amount);
        Uri contactUri  = getContentResolver().insert(ContactsProvider.CONTENT_URI1,values);
        Toast.makeText(this,Name + " Added",Toast.LENGTH_LONG).show();
    }

    private void deleteContact(String Name)
    {
        getContentResolver().delete(ContactsProvider.CONTENT_URI1,Name,new String[] {Name});
        Toast.makeText(this,Name + " Deleted", Toast.LENGTH_LONG).show();
    }

    private void deleteAllContacts()
    {

        getContentResolver().delete(ContactsProvider.CONTENT_URI1,null,null);
        restartLoader();
        Toast.makeText(this,"All Contacts Deleted",Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAllContacts:
                deleteAllContacts();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void restartLoader() {
        getLoaderManager().restartLoader(0,null,this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, ContactsProvider.CONTENT_URI1,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }


}
