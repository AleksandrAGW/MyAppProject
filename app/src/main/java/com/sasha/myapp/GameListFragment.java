package com.sasha.myapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

public class GameListFragment extends Fragment implements OnGameClickListener {

    private GameDataSource dataSource;
    private ArrayAdapter<Game> adapter;
    private ListView listViewGames;

    public GameListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_list, container, false);

        dataSource = new GameDataSource(requireContext());
        dataSource.open();

        listViewGames = view.findViewById(R.id.listViewGames);
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, dataSource.getAllGames());
        listViewGames.setAdapter(adapter);
        listViewGames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onGameClick(adapter.getItem(position));
            }
        });

        return view;
    }

    @Override
    public void onGameClick(Game game) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Game Details: " + game.getTitle())
                .setMessage("Genre: " + game.getGenre() + "\n"
                        + "Platform: " + game.getPlatform())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }
}