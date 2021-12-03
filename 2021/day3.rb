input = "00100
11110
10110
10111
10101
01111
00111
11100
10000
11001
00010
01010"

numbers = File.read('day3.txt').split("\n")
#numbers = input.split("\n")

gamma = ''
epsylon = ''

(0..numbers[0].size-1).each do |index|
  ones = numbers.filter { |number| number[index] == '1' }.size
  zeros = numbers.filter { |number| number[index] == '0' }.size

  if ones > zeros
    gamma += '1'
    epsylon += '0'
  else
    gamma += '0'
    epsylon += '1'
  end
end

p gamma.to_i(2) * epsylon.to_i(2)


oxygen = numbers
co2 = numbers

(0..numbers[0].size-1).each do |index|
  ones = oxygen.filter { |number| number[index] == '1' }.size
  zeros = oxygen.filter { |number| number[index] == '0' }.size

  if ones >= zeros
    oxygen = oxygen.filter { |number| number[index] == '1' } if oxygen.size > 1
  else
    oxygen = oxygen.filter { |number| number[index] == '0' } if oxygen.size > 1
  end
  
  ones = co2.filter { |number| number[index] == '1' }.size
  zeros = co2.filter { |number| number[index] == '0' }.size

  if ones >= zeros
    co2 = co2.filter { |number| number[index] == '0' } if co2.size > 1
  else
    co2 = co2.filter { |number| number[index] == '1' } if co2.size > 1
  end
end

p oxygen
p co2

p oxygen[0].to_i(2) * co2[0].to_i(2)
