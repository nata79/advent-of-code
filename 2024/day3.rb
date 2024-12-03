total = 0

File.read('day3.txt').scan(/mul\((\d+),(\d+)\)/).each do |match|
  total += match.map(&:to_i).reduce(:*)
end

p total


total = 0
state = 'do'

File.read('day3.txt').scan(/(do\(\))|(don\'t\(\))|mul\((\d+),(\d+)\)/).each do |match|
  if match[0]
    state = 'do'
  elsif match[1]
    state = 'don\'t'
  elsif match[2]
    if state == 'do'
      total += match[2].to_i * match[3].to_i
    end
  end
end

p total