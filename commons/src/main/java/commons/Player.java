package commons;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

    @Entity
    public class Player {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)

        public String userName;
        public int score;

        @SuppressWarnings("unused")
        private Player() {
            // for object mapper
        }

        /**
         * Create a Player Object
         * @param userName - the userName of the player
         * @param score - his highest score
         */
        public Player(String userName, int score) {
            this.userName = userName;
            this.score = score;
        }

        /**
         * Getter for the userName of the object Player
         * @return the userName of the object Player
         */

        public String getUserName() {
            return userName;
        }

        /**
         * Setter for the userName of the object Player
         * @param newUserName - the new userName for the object Player
         */

        public void setUserName(String newUserName){
            this.userName=newUserName;
        }

        /**
         * Getter for the score of the object Player
         * @return the score of the object Player
         */

        public long getScore() {
            return score;
        }

        /**
         * Setter for the score of the object Player
         * @param score - the new score for the obejct Player
         */

        public void setScore(int score) {
            this.score = score;
        }

        @Override
        public boolean equals(Object obj) {

            if (obj==this) return true;

            if (obj instanceof Player){
                Player that = (Player) obj;
                if (that.getUserName().equals(this.getUserName()) && that.getScore()==this.getScore()) return true;
            }

            return false;
        }

        @Override
        public String toString() {
            return "Username: "+this.getUserName()+"\nHighest score: "+this.getScore();
        }
    }
