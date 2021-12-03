instructions = File.read('day2.txt').split("\n")

x = 0
y = 0

instructions.each do |instruction|
  direction, total = instruction.split(' ')
  
  case direction
  when 'forward'
    x += total.to_i
  when 'down'
    y += total.to_i
  when 'up'
    y -= total.to_i
  end
end

p x*y


aim = 0
x = 0
y = 0

instructions.each do |instruction|
  direction, total = instruction.split(' ')
  
  case direction
  when 'forward'
    x += total.to_i
    y += aim * total.to_i
  when 'down'
    aim += total.to_i
  when 'up'
    aim -= total.to_i
  end
end

p x*y
