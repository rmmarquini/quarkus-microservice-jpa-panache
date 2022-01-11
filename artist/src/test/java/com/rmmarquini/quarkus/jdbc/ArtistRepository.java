package com.rmmarquini.quarkus.jdbc;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Random;

@ApplicationScoped
public class ArtistRepository {

    @Inject
    DataSource dataSource;

    public void persist(Artist artist) throws SQLException {

        Connection conn = dataSource.getConnection();

        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO t_artists (id, name, bio, created_at) ");
        sql.append(" VALUES (?, ?, ?, ?) ");

        artist.setId(Math.abs(new Random().nextLong()));

        try (PreparedStatement pst = conn.prepareStatement(sql.toString())) {

            pst.setLong(1, artist.getId());
            pst.setString(2, artist.getName());
            pst.setString(3, artist.getBio());
            pst.setTimestamp(4, Timestamp.from(artist.getCreatedAt()));

            pst.executeUpdate();

        } finally {
            conn.close();
        }

    }

    public Artist findById(Long id) throws SQLException {

        Connection conn = dataSource.getConnection();

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT id, name, bio, created_at ");
        sql.append(" FROM t_artists ");
        sql.append(" WHERE id = ? ");

        Artist artist = new Artist();

        try (PreparedStatement pst = conn.prepareStatement(sql.toString())) {

            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    artist.setId(rs.getLong(1));
                    artist.setName(rs.getString(2));
                    artist.setBio(rs.getString(3));
                    artist.setCreatedAt(rs.getTimestamp(4).toInstant());
                }
            }

        } finally {
            conn.close();
        }

        return artist;
    }

}