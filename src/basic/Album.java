package basic;

import java.util.List;

public class Album {

    private String nameOfAlbum;
    private List<Song> albumListOfSongs;

    public Album(String nameOfAlbum, List<Song> albumListOfSongs) {
        this.nameOfAlbum = nameOfAlbum;
        this.albumListOfSongs = albumListOfSongs;
    }

    public List<Song> getAlbumListOfSongs() {
        return albumListOfSongs;
    }

    public void setAlbumListOfSongs(List<Song> albumListOfSongs) {
        this.albumListOfSongs = albumListOfSongs;
    }

    public String getNameOfAlbum() {
        return nameOfAlbum;
    }

    public void setNameOfAlbum(String nameOfAlbum) {
        this.nameOfAlbum = nameOfAlbum;
    }

    public void addSongInAlbum(String nameOfSong, Integer duration) {
        Song song = findSong(nameOfSong);
        if (song == null) {
            Song alb = new Song(nameOfSong, duration);
            this.albumListOfSongs.add(alb);
        }
    }

    private Song findSong(String nameOfSong) {
        for (int i = 0; i < albumListOfSongs.size(); i++) {
            if (albumListOfSongs.get(i).getTitle().equalsIgnoreCase(nameOfSong)) {
                return albumListOfSongs.get(i);
            }
        }
        return null;
    }

    public String returnStringFindSong(String nameOfSong) {
        Song song = findSong(nameOfSong);
        return song.getTitle();
    }

    public boolean removeSong(String nameOfSong) {
        Song song = findSong(nameOfSong);
        if (song != null) {
            albumListOfSongs.remove(song);
            return true;
        }
        return false;
    }

    public int findSongIndex(String nameOfSong) {
        Song song = findSong(nameOfSong);
        return albumListOfSongs.indexOf(song);
    }

    public void printSongs() {
        for (int i = 0; i < albumListOfSongs.size(); i++) {
            System.out.println("- " + albumListOfSongs.get(i).getTitle());
        }
    }
}

