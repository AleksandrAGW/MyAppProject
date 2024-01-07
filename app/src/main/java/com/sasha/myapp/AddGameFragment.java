package com.sasha.myapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class AddGameFragment extends Fragment {

    private GameDataSource dataSource;
    private EditText editTextTitle;
    private EditText editTextGenre;
    private EditText editTextPlatform;

    public AddGameFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_game, container, false);

        dataSource = new GameDataSource(requireContext());
        dataSource.open();

        editTextTitle = view.findViewById(R.id.editTextTitle);
        editTextGenre = view.findViewById(R.id.editTextGenre);
        editTextPlatform = view.findViewById(R.id.editTextPlatform);

        Button buttonAddGame = view.findViewById(R.id.buttonAddGame);
        buttonAddGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGame();
            }
        });

        return view;
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

                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new GameListFragment())
                        .addToBackStack(null)
                        .commit();
            } else {
                showMessage("Помилка додавання гри");
            }
        } else {
            showMessage("Будь ласка, заповніть всі поля");
        }

        clearInputFields();
    }


    private void clearInputFields() {
        editTextTitle.getText().clear();
        editTextGenre.getText().clear();
        editTextPlatform.getText().clear();
    }

    private void showMessage(String message) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }
}