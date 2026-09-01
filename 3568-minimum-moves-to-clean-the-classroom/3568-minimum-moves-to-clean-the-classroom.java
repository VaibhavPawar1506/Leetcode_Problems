import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();
        
        // Find starting position 'S' and count total litter 'L'
        int startR = -1, startC = -1;
        int totalLitter = 0;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char c = classroom[i].charAt(j);
                if (c == 'S') {
                    startR = i;
                    startC = j;
                } else if (c == 'L') {
                    totalLitter++;
                }
            }
        }
        
        // The target mask when all litter is collected (e.g., if 3 L's, target is 111 in binary = 7)
        int fullMask = (1 << totalLitter) - 1;
        
        // Directions: Up, Down, Left, Right
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        
        // Queue for BFS: stores {row, col, mask, energy, steps}
        // Using an array for the state to keep it simple
        Queue<int[]> queue = new LinkedList<>();
        
        // visited[row][col][mask] stores the MAX energy we had at this state
        // If we reach (r, c, mask) again with <= energy, we skip it.
        int[][][] maxEnergy = new int[m][n][1 << totalLitter];
        for (int[][] layer : maxEnergy) {
            for (int[] row : layer) {
                Arrays.fill(row, -1);
            }
        }
        
        // Map to track which 'L' index corresponds to which cell
        // We need to assign an ID (0 to totalLitter-1) to each 'L' cell
        int[][] litterId = new int[m][n];
        for (int[] row : litterId) Arrays.fill(row, -1);
        
        int currentLitterId = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (classroom[i].charAt(j) == 'L') {
                    litterId[i][j] = currentLitterId++;
                }
            }
        }
        
        // Initial state
        int startMask = 0;
        // If start is on an 'L' (unlikely per constraints but good to handle)
        if (classroom[startR].charAt(startC) == 'L') {
            startMask |= (1 << litterId[startR][startC]);
        }
        
        queue.offer(new int[]{startR, startC, startMask, energy, 0});
        maxEnergy[startR][startC][startMask] = energy;
        
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];
            int mask = curr[2];
            int currEnergy = curr[3];
            int steps = curr[4];
            
            // Check if all litter is collected
            if (mask == fullMask) {
                return steps;
            }
            
            // Try all 4 directions
            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];
                
                // Check bounds and obstacles
                if (nr >= 0 && nr < m && nc >= 0 && nc < n && classroom[nr].charAt(nc) != 'X') {
                    int newEnergy = currEnergy - 1;
                    int newMask = mask;
                    int newSteps = steps + 1;
                    
                    // If we hit a reset area 'R', energy is restored
                    if (classroom[nr].charAt(nc) == 'R') {
                        newEnergy = energy;
                    }
                    
                    // If we hit litter 'L', update mask
                    if (classroom[nr].charAt(nc) == 'L') {
                        int lId = litterId[nr][nc];
                        if ((mask & (1 << lId)) == 0) {
                            newMask |= (1 << lId);
                        }
                    }
                    
                    // CRITICAL: If energy drops to 0, we can only proceed if we are ON 'R'
                    // But wait: The rule says "If energy reaches 0, the student can only continue if they are on a reset area".
                    // This implies if newEnergy is 0 AND the current cell (nr, nc) is NOT 'R', we are stuck.
                    // However, the move is valid only if we can actually make the move.
                    // If we move TO a cell and energy becomes 0, we are now AT that cell.
                    // If that cell is NOT 'R', we cannot make the NEXT move.
                    // But can we make THIS move? Yes, as long as we had energy > 0 to start the move.
                    // The constraint "If energy reaches 0... can only continue if on R" means:
                    // If newEnergy == 0, we can only be in this state if classroom[nr][nc] == 'R'.
                    // Otherwise, we are stuck with 0 energy and cannot move again, so this path is dead.
                    
                    if (newEnergy == 0 && classroom[nr].charAt(nc) != 'R') {
                        // We are stuck here. This path is invalid for future moves.
                        // However, if we collected the LAST piece of litter here, we win!
                        // But if mask == fullMask, we would have returned above.
                        // So if we are here with 0 energy and not on R, we can't proceed.
                        // We can still record this state if it's the final answer?
                        // No, because we check mask == fullMask at the start of processing the popped item.
                        // If we arrive here with 0 energy and not on R, we can't process further moves.
                        // But if we just collected the last litter, we might return steps+1?
                        // Actually, the check `if (mask == fullMask)` is done when we POP from queue.
                        // If we arrive at (nr, nc) with newEnergy=0 and not R, we can push it to queue?
                        // If we push it, when we pop it, we check mask. If full, return. If not, we try moves.
                        // If we try moves with 0 energy, we fail immediately.
                        // Optimization: Don't push if newEnergy == 0 and not on R, UNLESS it's the final state?
                        // Actually, if newEnergy == 0 and not R, we can't make any MORE moves.
                        // So if mask != fullMask, this state is a dead end.
                        // If mask == fullMask, we are done.
                        // Let's just push it and let the loop handle it? Or prune?
                        // Pruning: if newEnergy == 0 and not R and newMask != fullMask, skip.
                        // Wait, if newMask == fullMask, we don't need to move anymore.
                        // So we can push it.
                    }
                    
                    // If energy is 0 and we are not on R, we cannot make further moves.
                    // But we can still be in this state.
                    // However, if newEnergy < 0, it's impossible (shouldn't happen as we check before move)
                    
                    // Optimization: If newEnergy == 0 and not R and newMask != fullMask, skip adding to queue
                    // because we can't move from here.
                    if (newEnergy == 0 && classroom[nr].charAt(nc) != 'R' && newMask != fullMask) {
                        continue;
                    }

                    // Check if this state is better than what we've seen
                    if (newEnergy > maxEnergy[nr][nc][newMask]) {
                        maxEnergy[nr][nc][newMask] = newEnergy;
                        queue.offer(new int[]{nr, nc, newMask, newEnergy, newSteps});
                    }
                }
            }
        }
        
        return -1;
    }
}