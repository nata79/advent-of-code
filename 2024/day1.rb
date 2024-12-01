left = []
right = []

File.read('day1.txt').split("\n").map do 
  a, b = _1.split('   ')
  left << a.to_i
  right << b.to_i
end

left.sort!
right.sort!

total = 0

left.each_with_index do |l, i|
  total += (l - right[i]).abs
end

p total

score = 0

left.each do |l|
  score += l * right.filter { _1 == l }.size
end

p score