
class Numbers
{
int num[];

Numbers(int num[])
{

this.num = num;

}

int getTotal()
{

int total = 0;

for(int cnt = 0; cnt<num.length; cnt++)

total += num[cnt];

return total;
}

int getAverage()
{

int total = 0;
int average = 0;

total = getTotal();

average = total/num.length;

return average;

}
}


