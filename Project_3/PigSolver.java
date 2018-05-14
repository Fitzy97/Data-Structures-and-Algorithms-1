
public class PigSolver {

	//nWins: holds values for wins(n, x, y) - rows x, columns y
	private double[][] nWins;

	//nM1Wins: holds values for wins(n-1, x, y) - rows x, columns y
	private double[][] nM1Wins;

	//nM2Wins: holds values for wins(n-2, x, y) - rows x, columns y
	private double[][] nM2Wins;

	PigProbabilities pigProbability;

	//number of iterations
	private int nValue;

	//game target
	private int TValue;

	//optimal score to roll to
	private int SValue;

	//Constructor: instantiates all the variables, fills in the three 2D arrays, T: game target score, x: player 1's score, y: player 2's score
	public PigSolver(int T, int x, int y)
	{
		pigProbability = new PigProbabilities(T);

		TValue = T;

		//SValue always starts at 2
		SValue = 2;

		nM2Wins = new double[T+7][T+7];


		//fill in nM2Wins for when n=0
		for (int i = 0; i < T+7; i++)
		{
			for (int j = 0; j < T+7; j++)
			{
				//if we won already
				if (i >= T)
				{
					nM2Wins[i][j] = 1.0;
				}
				//if we lost already
				else if (j >= T)
				{
					nM2Wins[i][j] = 0.0;
				}
				else
				{
					nM2Wins[i][j] = 0.5;
				}
			}
		}

		nM1Wins = new double[T+7][T+7];

		//fills in nM1Wins for when n=1
		for (int i = 0; i < T+7; i++)
		{
			for (int j = 0; j < T+7; j++)
			{
				if (i >= T)
				{
					nM1Wins[i][j] = 1.0;
				}
				else if (j >= T)
				{
					nM1Wins[i][j] = 0.0;
				}
				else
				{
					nM1Wins[i][j] = probOdd(1, i, j);	
				}
			}
		}

		nWins = new double[T+7][T+7];

		//fills in nWins for when n=2
		for (int i = 0; i < T+7; i++)
		{
			for (int j = 0; j < T+7; j++)
			{
				if (i >= T)
				{
					nWins[i][j] = 1.0;
				}
				else if (j >= T)
				{
					nWins[i][j] = 0.0;
				}
				else
				{
					nWins[i][j] = probEven(2, i, j, x, y);
				}
			}
		}

		//already have values for n=0,1,2
		int n = 3;

		//iterates until values for wins(n, x, y) == wins(n-2, x, y)
		while (nWins[x][y] != nM2Wins[x][y])
		{
			//fill values of nM2Wins with values of wins with the next value of n
			updateN(n, x, y);
			n++;
		}

		if (n%2 != 0)
		{
			updateN(n, x, y);
		}

		nValue = n;
	}

	//fills nM2Wins with values of wins with the next value of n
	public void updateN(int n, int x, int y)
	{
		//fill values of nM2Wins with values of wins with the next value of n
		for (int i = 0; i < TValue+7; i++)
		{
			for (int j = 0; j < TValue+7; j++)
			{
				if (i >= TValue)
				{
					nM2Wins[i][j] = 1.0;
				}
				else if (j >= TValue)
				{
					nM2Wins[i][j] = 0.0;
				}
				else if (n % 2 == 0)
					nM2Wins[i][j] = probEven(n, i, j, x, y);
				else
					nM2Wins[i][j] = probOdd(n, i, j);
			}
		}

		double[][] temp = nWins;
		nWins = nM2Wins;
		nM2Wins = nM1Wins;
		nM1Wins = temp;
	}

	//return number of iterations (k)
	public int iterations()
	{
		return nValue;
	}

	//returns optimal score to roll to
	public int bestRoll(int x, int y)
	{
		return SValue;
	}

	//return probability that player 1 will win with scores x-y
	public double wins(int x, int y)
	{
		if (nValue == 0)
		{
			return nM2Wins[x][y];
		}
		else if (nValue == 1)
		{
			return nM1Wins[x][y];
		}
		else
		{
			return nWins[x][y];
		}
	}

	//returns value of wins(n, i, j) when n is even - when it's player 1's turn
	public double probEven(int n, int i, int j, int x, int y)
	{
		double maxValue = nIsEven(n, i, j, 2);

		//find the maximum probability that player 1 will win for each turn target between player 1's score and the game target
		for (int S = 2; S <= Math.max(2, TValue-i); S++)
		{
			double currentValue = nIsEven(n, i, j, S);
			if (currentValue > maxValue) 
			{
				maxValue = currentValue;

				//if i and j correspond with the user's unputs for x and y, the optimal turn target is S
				if (i == x && j == y)
				{
					SValue = S;
				}
			}
		}

		return maxValue;
	}

	//returns value of wins(n, i, j) when n is odd - when it's player 2's turn
	public double probOdd(int n, int i, int j)
	{
		double minValue = nIsOdd(n, i, j, 2);

		//find the minimum probability that player 1 will win for each turn target between player 2's score and the game target
		for (int S = 2; S <= Math.max(2, TValue-j); S++)
		{
			double currentValue = nIsOdd(n, i, j, S);
			if (currentValue < minValue)
			{
				minValue = currentValue;
			}
		}

		return minValue;
	}

	//Computes the probability player1 will win for a specific even number of turns left (n), 
	//a specific score (i-j), and a specific turn target (S).  Will only be called when it's player 1's turn.
	public double nIsEven(int n, int i, int j, int S)
		{
			//probability that player 1 will roll a 1
			double probability = pigProbability.pEndAt(S, 0);

			//the n-1 array to pull values from
			double[][] previousArray;

			//special case
			if (n == 2)
			{
				previousArray = nM1Wins;
			}
			else 
			{
				previousArray = nWins;
			}

			//wins(n-1, x, y)
			double previousWinsValue = previousArray[i][j];

			double summation = 0;
			//add up all the probabilites that we reach the turn target or roll over it
			for (int s = S; s <= S + 5; s++)
				{
					summation += previousArray[i+s][j] * pigProbability.pEndAt(S, s);
				}

			return previousWinsValue * probability + summation;
		}

	//Computes the probability player2 will win for a specific number of turns left (n), 
	//a specific score (i-j), and a specific turn target (S).  Will only be called when it's player 2's turn.
	public double nIsOdd(int n, int i, int j, int S)
	{
		//probability that player2 rolls a 1
		double probability = pigProbability.pEndAt(S, 0);

		//array with wins(n-1) values to pull values from
		double[][] previousArray;

		//special case
		if (n == 1)
		{
			previousArray = nM2Wins;
		}
		else 
		{
			previousArray = nWins;
		}

		//wins(n-1, x, y)
		double previousWinsValue = previousArray[i][j];

		double summation = 0;
		//add up all the probabilities that player2 reaches the turn target or over
		for (int s = S; s <= S + 5; s++)
		{
			summation += previousArray[i][j+s] * pigProbability.pEndAt(S, s);
		}

		return previousWinsValue * probability + summation;
	}

	public static void main(String[] args) 
		{
			int T = Integer.parseInt(args[0]);
			int x = Integer.parseInt(args[1]);
			int y = Integer.parseInt(args[2]);

			PigSolver p = new PigSolver(T, x, y);
			System.out.println( p.iterations() + " iterations");
			System.out.println( p.wins(x, y) + " " + p.bestRoll(x, y));
		}
}