lines = File.read('day2.txt').split("\n")

valid_passwords = 0

lines.each do |line|
  # 3-6 s: ssdsssss
  password = line.split(':')[1].strip
  rule = line.split(':')[0]
  letter = rule.split(' ')[1]
  range = rule.split(' ')[0].split('-')[0].to_i...(rule.split(' ')[0].split('-')[1].to_i + 1)

  valid_passwords += 1 if range.include? password.chars.filter { |l| l == letter }.size
end

p valid_passwords

valid_passwords = 0

lines.each do |line|
  # 3-6 s: ssdsssss
  password = line.split(':')[1].strip
  rule = line.split(':')[0]
  letter = rule.split(' ')[1]
  position_1 = rule.split(' ')[0].split('-')[0].to_i - 1
  position_2 = rule.split(' ')[0].split('-')[1].to_i - 1

  if (password.chars[position_1] == letter && password.chars[position_2] != letter) || (password.chars[position_1] != letter && password.chars[position_2] == letter)
    valid_passwords += 1
  end
end

p valid_passwords
