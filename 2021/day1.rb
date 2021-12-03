numbers = File.read('day1-1.txt').split("\n").map(&:to_i)

increased = 0

numbers.each_with_index do |number, index|
  increased += 1 if index > 0 && number > numbers[index - 1]
end

p increased

prev = nil
increased = 0
numbers.each_with_index do |number, index|
  current = numbers.dig(index - 1) + numbers.dig(index - 2) + number
  increased += 1 if prev && current > prev
  prev = current
end

p increased
