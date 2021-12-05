input = "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

22 13 17 11  0
 8  2 23  4 24
21  9 14 16  7
 6 10  3 18  5
 1 12 20 15 19

 3 15  0  2 22
 9 18 13 17  5
19  8  7 25 23
20 11 10 24  4
14 21 16 12  6

14 21 17 24  4
10 16 15  9 19
18  8 23 26 20
22 11 13  6  5
 2  0 12  3  7"

def winner?(board_matches)
  board_matches.each { |line| return true if line.all? }
  board_matches[0].size.times do |column|
    return true if board_matches.map { |line| line[column] }.all? { |match| match == true }
  end
  return false
end

#lines = input.split("\n").filter { |line| line.size > 0 }
lines = File.read('day4.txt').split("\n").filter { |line| line.size > 0 }

numbers = lines[0].split(',').map(&:to_i)

boards_input = lines.slice(1, lines.size)
number_of_boards = boards_input.size / 5

boards = []
matches = []
winners = []
winner_numbers = []

number_of_boards.times do |board_index|
  board = []
  board_matches = []
  5.times do |line_index|
    line = boards_input[(board_index * 5) + line_index].scan(/(\d\d|\d)/).flatten.map(&:to_i)
    board << line
    board_matches << [false] * 5
  end
  boards << board
  matches << board_matches
end

numbers.each do |number|
  boards.each_with_index do |board, i|
    next if winners.include? i
    
    board.each_with_index do |line, j|
      line.each_with_index do |element, z|
        matches[i][j][z] = true if element == number
      end      
    end
    
    if winner?(matches[i])
      winners << i
      winner_numbers << number
    end
  end
end

def score(boards, matches, i, number)
  score = 0
  boards[i].each_with_index do |line, j|
    line.each_with_index do |element, z|
      score += element unless matches[i][j][z]
    end
  end      
  
  score * number
end

p score(boards, matches, winners.first, winner_numbers.first)
p score(boards, matches, winners.last, winner_numbers.last)