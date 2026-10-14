public interface ITransport {
    String getName();

    boolean canCarry(double weight);
}