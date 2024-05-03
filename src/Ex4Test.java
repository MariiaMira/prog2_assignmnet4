public class Ex4Test {
    public static void main(String[] args){
        Exercise4 test = new Exercise4();
        //test.loadLocationGraph("ex4location.graph");

        test.loadRecommendationGraph("ex4reco.graph");
        test.getAlsoLiked(new Record("Destroyer", "KISS"));


    }
}
