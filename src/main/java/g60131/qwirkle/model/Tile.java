package g60131.qwirkle.model;

import java.io.Serializable;

/**
 * Cette classe est immutable et est une tuile de jeux.
 */
public record Tile (Color color, Shape shape) implements Serializable {}
