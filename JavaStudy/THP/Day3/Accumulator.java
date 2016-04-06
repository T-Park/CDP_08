class Accumulator

{

int total = 0;

static int grandTotal = 0;

void accumulate(int amount)

{

total += amount;

grandTotal += amount;

}

}