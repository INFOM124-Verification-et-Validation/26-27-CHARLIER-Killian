package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Neroan
 */
public class ClydeTest {
    PacManSprites sprites;
    GhostFactory ghostFactory;
    LevelFactory levelFactory;
    BoardFactory boardFactory;
    GhostMapParser ghostMapParser;
    Player player;

    @BeforeEach
    public void setUp() {
        this.sprites = new PacManSprites();
        this.ghostFactory = new GhostFactory(pacManSprites);
        this.levelFactory = new LevelFactory(pacManSprites, ghostFactory);
        this.boardFactory = new BoardFactory(pacManSprites);
        this.ghostMapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
        player = new PlayerFactory(pacManSprites).createPacMan();
    }

    /**
     * The expected direction is currently in the wall due to the way of Clyde movement
     */
    @Test
    public void testNextAiMove() {
        List<String> map = new ArrayList<>();
        map.add("###########");
        map.add("#P    #   #");
        map.add("#    #    #");
        map.add("#    #    #");
        map.add("#   # #   #");
        map.add("#C        #");
        map.add("###########");

        Level currentLevel = ghostMapParser.parseMap(map);
        currentLevel.registerPlayer(player);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, currentLevel.getBoard());

        Optional<Direction> aiDirection = clyde.nextAiMove();

        assertThat(aiDirection.get()).isEqualTo(Direction.SOUTH);
    }

    // Une partition détermine les comportements similaires
}
