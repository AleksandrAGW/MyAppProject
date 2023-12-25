package com.sasha.myapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnGameClickListener {

    private GameDataSource dataSource;
    private ArrayAdapter<Game> adapter;

    private EditText editTextTitle;
    private EditText editTextGenre;
    private EditText editTextPlatform;
    private ListView listViewGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new GameDataSource(this);
        dataSource.open();

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextGenre = findViewById(R.id.editTextGenre);
        editTextPlatform = findViewById(R.id.editTextPlatform);
        listViewGames = findViewById(R.id.listViewGames);

        Button buttonAddGame = findViewById(R.id.buttonAddGame);
        buttonAddGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGame();
                updateListView();
            }
        });

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataSource.getAllGames());
        listViewGames.setAdapter(adapter);

        listViewGames.setOnItemClickListener((parent, view, position, id) -> {
        Game clickedGame = adapter.getItem(position);
        onGameClick(clickedGame);
    });
}

    @Override
    public void onGameClick(Game game) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Details")
                .setMessage("Title: " + game.getTitle() + "\n"
                        + "Genre: " + game.getGenre() + "\n"
                        + "Platform: " + game.getPlatform())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addGame() {
        String title = editTextTitle.getText().toString();
        String genre = editTextGenre.getText().toString();
        String platform = editTextPlatform.getText().toString();

        if (!title.isEmpty() && !genre.isEmpty() && !platform.isEmpty()) {
            Game game = new Game();
            game.setTitle(title);
            game.setGenre(genre);
            game.setPlatform(platform);

            long rowId = dataSource.insertGame(game);
            if (rowId != -1) {
                showMessage("Гра додана успішно");
            } else {
                showMessage("Помилка додавання гри");
            }
        } else {
            showMessage("Будь ласка, заповніть всі поля");
        }

        clearInputFields();
    }

    private void updateListView() {
        adapter.clear();
        adapter.addAll(dataSource.getAllGames());
        adapter.notifyDataSetChanged();
    }

    private void clearInputFields() {
        editTextTitle.getText().clear();
        editTextGenre.getText().clear();
        editTextPlatform.getText().clear();
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }
}