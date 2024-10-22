import org.apache.commons.cli.*;

public class Main {
    public static void main(String[] args) {
        Options options = new Options();

        Option input = new Option("s", "server", true, "create server");
        options.addOption(input);

        Option output = new Option("c", "client", true, "create client");
        options.addOption(output);

        CommandLineParser parser = new BasicParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }

        if (cmd.hasOption("s")) {
            Server server = new Server(Integer.parseInt(cmd.getOptionValue("s")));
            server.createServer();
        } else if (cmd.hasOption("c")) {
            Client client = new Client("localhost", Integer.parseInt(cmd.getOptionValue("c")));
            client.start();
        } else {
            formatter.printHelp("utility-name", options);
            System.exit(1);
        }
    }
}